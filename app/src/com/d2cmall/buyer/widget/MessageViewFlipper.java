package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.AnimRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.d2cmall.buyer.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Jerome
 * @date 2017/12/01
 */
public class MessageViewFlipper extends ViewFlipper {
    private static final String TAG = "MessageViewFlipper";

    /**
     * 消息切换时间间隔，默认6s
     */
    private static final int FLIP_INTERVAL_TIME = 6 * 1000;

    /**
     * 省略号
     */
    private static final String ELLIPSIS = "...";

    /**
     * 创建TextView的属性，默认靠左对齐，并且垂直居中
     */
    private static final int LEFT_CENTER_VERTICAL = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    /**
     * 所有要展示的消息
     */
    private LinkedList<String> mMessages;

    /**
     * 当前展示的消息在集合{@link #mMessages}中的索引
     */
    private int mCurrentMessageIndex;

    /**
     * 被切割后的所有消息集合
     */
    private LinkedList<List<String>> mAllSplitMessages;

    /**
     * 正在显示的分割消息集合
     */
    private List<String> mCurrentSplitMessages;

    /**
     * 当前显示的被分割后的消息，在集合{@link #mCurrentSplitMessages}中的索引
     */
    private int mCurrentSplitMessageIndex;

    /**
     * 消息进入动画
     */
    private Animation mInAnim;

    /**
     * 消息出去动画
     */
    private Animation mOutAnim;

    /**
     * 文本字体颜色
     */
    private int mTextColor;

    private int mSplitMessageSize;

    public MessageViewFlipper(Context context) {
        this(context, null);
    }

    public MessageViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 添加默认要展示的消息，必须在{@link #start()}方法之前调用
     *
     * @param message 要添加的默认跑马灯消息
     */
    public void setMessage(String message) {
        mMessages.clear();
        mMessages.add(message);
    }

    /**
     * 将新的消息插入当前展示消息的后面
     * 插入新消息后必须调用{@link #notifyMessage()}方法来更新消息
     *
     * @param message 要插入的新消息
     */
    public void insertNewMessage(String message) {
        mMessages.add(mCurrentMessageIndex + 1, message);
        Log.d(TAG, "insertNewMessage: mMessages=" + mMessages.toString());
    }

    /**
     * 更新消息，在{@link #insertNewMessage(String)}方法之后调用
     * 在插入新的消息之后，分割较长的消息，并且把它们插入到分割后的消息集合中
     */
    public void notifyMessage() {
        List<String> spitMessages = spitMessage(mMessages.get(mCurrentMessageIndex + 1));
        mAllSplitMessages.add(mCurrentMessageIndex + 1, spitMessages);
        Log.d(TAG, "notifyMessage: mAllSplitMessages=" + mAllSplitMessages.toString());
        if (!isFlipping()) {
            setFlipInterval(3 * 1000);
            startFlipping();
            setFlipInterval(FLIP_INTERVAL_TIME);
        }
    }

    /**
     * 开始显示跑马灯
     */
    public void start() {
        if (mMessages == null || mMessages.size() == 0) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                int size = mMessages.size();
                for (int i = 0; i < size; i++) {
                    List<String> spitMessages = spitMessage(mMessages.get(i));
                    mSplitMessageSize = spitMessages.size();
                    mAllSplitMessages.add(spitMessages);
                }
                Log.d(TAG, "onGlobalLayout: mAllSplitMessages=" + mAllSplitMessages.toString());
                handleFlipping();
            }
        });
    }

    public void stop() {
        stopFlipping();
    }

    public void restart() {
        startFlipping();
    }

    private void init() {
        mMessages = new LinkedList<>();
        mAllSplitMessages = new LinkedList<>();
        setFlipInterval(FLIP_INTERVAL_TIME);
        setInAndOutAnimation(R.anim.statusbar_bottom_in, R.anim.statusbar_top_out);
    }

    private void handleFlipping() {
        if (mAllSplitMessages == null || mAllSplitMessages.size() == 0) {
            return;
        }
        removeAllViews();
        clearAnimation();

        mCurrentSplitMessages = mAllSplitMessages.get(mCurrentMessageIndex);
        Log.d(TAG, "handleFlipping: mCurrentSplitMessages=" + mCurrentSplitMessages.toString());

        TextView childView = createTextView(getMessage());
        addView(childView);

        if (mSplitMessageSize > 1) {
            startFlipping();
        }

        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    updateIndexAndMessages();

                    View childView = createTextView(getMessage());
                    if (childView.getParent() == null) {
                        addView(childView);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    /**
     * 更新校验消息索引
     * {@link #mCurrentMessageIndex}
     * {@link #mCurrentSplitMessageIndex}
     */
    private void updateIndexAndMessages() {
        mCurrentSplitMessageIndex++;
        if (mCurrentSplitMessageIndex >= mCurrentSplitMessages.size()) {
            mCurrentSplitMessageIndex = 0;
            // 集合索引+1，并对集合大小作取余运算，目的是循环
            mCurrentMessageIndex++;
            mCurrentMessageIndex = mCurrentMessageIndex % (mMessages.size());
            // 更新当前要展示的消息
            mCurrentSplitMessages = mAllSplitMessages.get(mCurrentMessageIndex);
        }
    }


    /**
     * 获取当前显示的消息
     *
     * @return 当前显示的消息
     */
    private String getMessage() {
        if (mAllSplitMessages != null && mAllSplitMessages.size() > 0) {
            return mAllSplitMessages.get(mCurrentMessageIndex).get(mCurrentSplitMessageIndex);
        } else {
            return null;
        }
    }


    /**
     * 分割消息
     * 规则：当一条消息超过控件宽度，则当前行最后一个字符以"..."结尾，下一行以"..."开头
     *
     * @param message 要展示的消息
     */
    private List<String> spitMessage(String message) {
        List<String> splitMessages = new ArrayList<>(3);
        TextView tv = createTextView("");
        // 省略号"..."的宽度
        final float ellipsisLength = tv.getPaint().measureText(ELLIPSIS);
        //Paint，包含字体等信息
        final Paint tvPaint = tv.getPaint();
        //TextView可用宽度
        final float tvWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        // 获取文本宽度
        float messageWidth = tvPaint.measureText(message);
        Log.d(TAG, "spitMessage: tvWidth=" + tvWidth);
        Log.d(TAG, "spitMessage: messageWidth=" + messageWidth);
        // 如果文本宽度不大于控件可用宽度，则直接存入集合中用于展示
        if (messageWidth <= tvWidth) {
            splitMessages.add(message);
        } else {
            // 整个message文本字符索引
            int charIndexInMessage = 0;
            // 单行文本宽度
            float singleLineMessageWidth;

            StringBuilder sb = new StringBuilder();
            // 第一次循环，将message分成多条存入集合中
            while (charIndexInMessage < message.length()) {
                char c = message.charAt(charIndexInMessage);
                sb.append(c);
                singleLineMessageWidth = tvPaint.measureText(sb.toString());
                if (singleLineMessageWidth < tvWidth) {
                    charIndexInMessage++;
                } else {
                    // 第二次循环，保证单条message和省略号"..."拼接后整体文本宽度不大于控件可用宽度
                    while (true) {
                        charIndexInMessage--;
                        sb.delete(sb.length() - 1, sb.length());
                        if (tvWidth - tvPaint.measureText(sb.toString()) >= ellipsisLength) {
                            sb.append(ELLIPSIS);
                            Log.d(TAG, "spitMessage: sb=" + sb.toString());
                            splitMessages.add(sb.toString());
                            sb = new StringBuilder();
                            sb.append(ELLIPSIS);
                            charIndexInMessage++;
                            break;
                        }
                    }
                }
            }
            splitMessages.add(sb.toString());
        }
        return splitMessages;
    }

    /**
     * 创建TextView
     *
     * @param message 要展示的单条message
     * @return 要显示的TextView
     */
    private TextView createTextView(String message) {
        TextView textView = (TextView) getChildAt((getDisplayedChild() + 1) % 3);
        if (textView == null) {
            textView = new TextView(getContext());
            textView.setSingleLine(true);
            if (mTextColor == 0) {
                mTextColor = Color.GRAY;
            }
            textView.setTextColor(mTextColor);
            textView.setGravity(LEFT_CENTER_VERTICAL);
        }
        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
        }
        return textView;
    }

    /**
     * 设置进入到退出的动画
     *
     * @param inAnimResId  进入动画
     * @param outAnimResId 退出动画
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResId) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResId);
        setInAnimation(inAnim);
        setOutAnimation(outAnim);
    }

}
