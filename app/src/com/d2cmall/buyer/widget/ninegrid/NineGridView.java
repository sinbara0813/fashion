package com.d2cmall.buyer.widget.ninegrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.d2cmall.buyer.R;

import java.util.ArrayList;
import java.util.List;

public class NineGridView extends ViewGroup {

    public static final int MODE_FILL = 0;         //填充模式，类似于微信
    public static final int MODE_GRID = 1;         //网格模式，类似于QQ，4张图会 2X2布局

    private int singleImageSize = 250;              // 单张图片时的最大大小,单位dp
    private float singleImageRatio = 1.0f;          // 单张图片的宽高比(宽/高)
    private int maxImageSize = 3;                   // 最大显示的图片数
    private int gridSpacing = 3;                    // 宫格间距，单位dp
    private int mode = MODE_FILL;                   // 默认使用fill模式

    private int columnCount;    // 列数
    private int rowCount;       // 行数
    private int gridWidth;      // 宫格宽度
    private int gridHeight;     // 宫格高度

    private List<ImageView> imageViews;
    private List<ImageInfo> mImageInfo;
    private NineGridViewAdapter mAdapter;
    private Context mContext;
    private boolean isNine;
    private boolean isFirst=true;

    public NineGridView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public NineGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        gridSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gridSpacing, dm);
        singleImageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, singleImageSize, dm);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        gridSpacing = (int) a.getDimension(R.styleable.NineGridView_ngv_gridSpacing, gridSpacing);
        singleImageSize = a.getDimensionPixelSize(R.styleable.NineGridView_ngv_singleImageSize, singleImageSize);
        singleImageRatio = a.getFloat(R.styleable.NineGridView_ngv_singleImageRatio, singleImageRatio);
        isNine=a.getBoolean(R.styleable.NineGridView_ngv_isNine,false);
        if (isNine){
            maxImageSize = a.getInt(R.styleable.NineGridView_ngv_maxSize, maxImageSize);
        }
        mode = a.getInt(R.styleable.NineGridView_ngv_mode, mode);
        a.recycle();

        imageViews = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImageInfo != null && mImageInfo.size() > 0) {
            /*if (mImageInfo.size() == 1) {
                //gridWidth = singleImageSize > totalWidth ? totalWidth : singleImageSize;
                gridWidth=totalWidth;
                gridHeight = (int) (gridWidth / singleImageRatio);
                //矫正图片显示区域大小，不允许超过最大显示范围
                *//*if (gridHeight > singleImageSize) {
                    float ratio = singleImageSize * 1.0f / gridHeight;
                    gridWidth = (int) (gridWidth * ratio);
                    gridHeight = singleImageSize;
                }*//*
            } else if (mImageInfo.size() == 2) {
                gridWidth = gridHeight = (totalWidth - gridSpacing) / 2;
            } else {
                //这里无论是几张图片，宽高都按总宽度的 1/3
                gridWidth = gridHeight = (totalWidth - gridSpacing * 2) / columnCount;
                if (!isNine){
                    rowCount=2;
                }
            }*/
            gridWidth = gridHeight = (totalWidth - gridSpacing * 2) / columnCount;
            if (!isNine&&mImageInfo.size()>2){
                rowCount=2;
            }
            width = gridWidth * columnCount + gridSpacing * (columnCount - 1) + getPaddingLeft() + getPaddingRight();
            height = gridHeight * rowCount + gridSpacing * (rowCount - 1) + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mImageInfo == null) return;
        if (isFirst){
            int childrenCount = mImageInfo.size();
            ImageView childrenView=null;
            int left=0;
            int top=0;
            int right=0;
            int bottom=0;
            if (childrenCount==1||childrenCount==2||isNine){
                for (int i = 0; i < childrenCount; i++) {
                    childrenView = (ImageView) getChildAt(i);
                    if (mAdapter != null) {
                        mAdapter.onDisplayImage(getContext(), childrenView, mImageInfo.get(i), childrenCount);
                    }
                    int rowNum = i / columnCount;
                    int columnNum = i % columnCount;
                    left = (gridWidth + gridSpacing) * columnNum + getPaddingLeft();
                    top = (gridHeight + gridSpacing) * rowNum + getPaddingTop();
                    right = left + gridWidth;
                    bottom = top + gridHeight;
                    childrenView.layout(left, top, right, bottom);
                }
            }else{
                for (int i = 0; i < childrenCount; i++) {
                    if (i==3)
                        break;
                    childrenView = (ImageView) getChildAt(i);
                    if (mAdapter != null) {
                        mAdapter.onDisplayImage(getContext(), childrenView, mImageInfo.get(i), childrenCount);
                    }
                    if (i==0){
                        left =  getPaddingLeft();
                        top =  getPaddingTop();
                        right = left + gridWidth*2+gridSpacing;
                        bottom = top + gridHeight*2+gridSpacing;
                    }else if (i==1){
                        left =getPaddingLeft()+gridWidth*2+gridSpacing*2;
                        top=getPaddingTop();
                        right=left+gridWidth;
                        bottom=top+gridHeight;
                    }else if (i==2){
                        left =getPaddingLeft()+gridWidth*2+gridSpacing*2;
                        top=getPaddingTop()+gridHeight*1+gridSpacing;
                        right=left+gridWidth;
                        bottom=top+gridHeight;
                    }
                    childrenView.layout(left, top, right, bottom);
                }
            }
            isFirst=false;
        }
    }

    /**
     * 设置适配器
     */
    public void setAdapter(@NonNull NineGridViewAdapter adapter) {
        mAdapter = adapter;
        List<ImageInfo> imageInfo = adapter.getImageInfo();

        if (imageInfo == null || imageInfo.isEmpty()) {
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }

        int imageCount = imageInfo.size();
        if (maxImageSize > 0 && imageCount > maxImageSize) {
            imageInfo = imageInfo.subList(0, maxImageSize);
            imageCount = imageInfo.size();   //再次获取图片数量
        }

        //默认是3列显示，行数根据图片的数量决定
        rowCount = imageCount / 3 + (imageCount % 3 == 0 ? 0 : 1);
        columnCount = 3;
        //grid模式下，显示4张使用2X2模式
        if (mode == MODE_GRID) {
            if (imageCount == 4) {
                rowCount = 2;
                columnCount = 2;
            }
        }

        //保证View的复用，避免重复创建
        if (mImageInfo == null) {
            for (int i = 0; i < imageCount; i++) {
                View iv = getImageView(i);
                if (iv == null) return;
                addView(iv, generateDefaultLayoutParams());
            }
        } else {
            int oldViewCount = mImageInfo.size();
            int newViewCount = imageCount;
            /*if (oldViewCount > newViewCount) {
                removeViews(newViewCount, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = oldViewCount; i < newViewCount; i++) {
                    View iv = getImageView(i);
                    if (iv == null) return;
                    addView(iv, generateDefaultLayoutParams());
                }
            }*/
            removeViews(0, oldViewCount);
            for (int i = 0; i < newViewCount; i++) {
                View iv = getImageView(i);
                if (iv == null) return;
                addView(iv, generateDefaultLayoutParams());
            }
        }
        //修改最后一个条目，决定是否显示更多
        if (adapter.getImageInfo().size() > maxImageSize) {
            View child = getChildAt(maxImageSize - 1);
            if (child instanceof NineGridViewWrapper) {
                NineGridViewWrapper imageView = (NineGridViewWrapper) child;
                imageView.setMoreNum(adapter.getImageInfo().size() - maxImageSize);
            }
        }
        mImageInfo = imageInfo;
        requestLayout();
    }

    /**
     * 获得 ImageView 保证了 ImageView 的重用
     */
    private View getImageView(final int position) {
        ImageView imageView;
        if (position < imageViews.size()) {
            imageView = imageViews.get(position);
        } else {
            imageView = mAdapter.generateImageView(getContext());
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.onImageItemClick(getContext(), NineGridView.this, position, mAdapter.getImageInfo());
                }
            });
            imageViews.add(imageView);
        }
        return imageView;
    }

    /**
     * 设置宫格间距
     */
    public void setGridSpacing(int spacing) {
        gridSpacing = spacing;
    }

    /**
     * 设置只有一张图片时的宽
     */
    public void setSingleImageSize(int maxImageSize) {
        singleImageSize = maxImageSize;
    }

    /**
     * 设置只有一张图片时的宽高比
     */
    public void setSingleImageRatio(float ratio) {
        singleImageRatio = ratio;
    }

    /**
     * 设置最大图片数
     */
    public void setMaxSize(int maxSize) {
        maxImageSize = maxSize;
    }

    public int getMaxSize() {
        return maxImageSize;
    }
}