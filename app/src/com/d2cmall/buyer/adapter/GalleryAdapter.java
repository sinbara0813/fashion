package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.Photo;
import com.d2cmall.buyer.bean.PhotoDirectory;
import com.d2cmall.buyer.util.MediaManager;
import com.d2cmall.buyer.widget.SquareImageView;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/3/14.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {
    private PhotoDirectory images;
    private LayoutInflater layoutInflater;
    private AdapterView.OnItemClickListener onItemClickListener;
    private RecyclerView imageRecyclerView;
    private ImageView lastCheckBox;
    private SquareImageView lastThum;

    public GalleryAdapter(Activity activity, PhotoDirectory images) {
        this.images = images;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setImageRecyclerView(RecyclerView imageRecyclerView) {
        this.imageRecyclerView = imageRecyclerView;
    }

    public void setImages(PhotoDirectory images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public PhotoDirectory getImages() {
        return images;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        String mimeType = images.getPhotos().get(position).getMimetype();
        return mimeType.contains("video") ? 2 : 1;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = viewType == 2 ? R.layout.gallery_video_item : R.layout.gallery_image_item;
        View view = layoutInflater.inflate(layoutRes, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryHolder holder, final int position) {

        final Photo photo = images.getPhotos().get(position);
        String url = "file:///" + photo.getPath();
        boolean check = MediaManager.getInstance().exsit(photo.getId());
        holder.thumbIv.justSetShowShade(check);
        Glide.with(holder.thumbIv.getContext()).load(url).into(holder.thumbIv);
        holder.appCompatCheckBox.setImageResource(check?R.mipmap.ic_square_checked:R.mipmap.ic_square_unchecked);
        if (photo.getMimetype().contains("video")) {
            holder.tvVideoDuration.setText(converDuration(photo.getDuration()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = MediaManager.getInstance().exsit(photo.getId());
                if (isChecked) {
                    MediaManager.getInstance().removeMedia(photo.getId(), true);
                    holder.appCompatCheckBox.setImageResource(R.mipmap.ic_square_unchecked);
                    holder.thumbIv.setShowShade(false);
                    lastCheckBox=null;
                    lastThum=null;
                } else {
                    MediaManager.getInstance().addMedia(photo.getId(), photo, true);//表示Checkbox已经更新过了，不需要再次进行更新，主要用于区分再其他地方更新了选择状态，但是这边的UI，没更新的情况。
                    holder.thumbIv.setShowShade(true);
                    holder.appCompatCheckBox.setImageResource(R.mipmap.ic_square_checked);
                    if (lastCheckBox!=null){
                        lastCheckBox.setImageResource(R.mipmap.ic_square_unchecked);
                        lastThum.setShowShade(false);
                    }
                    lastCheckBox=holder.appCompatCheckBox;
                    lastThum=holder.thumbIv;
                }
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(GalleryHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    String converDuration(long duration) {
        StringBuilder durationString = new StringBuilder();
        int second = (int) (duration / 1000);
        int min = second / 60;
        int hour = min / 60;
        if (hour > 0)
            durationString.append(hour + ":");
        durationString.append(min + ":");
        durationString.append(new DecimalFormat("00").format(second%60));
        return durationString.toString();
    }

    @Override
    public int getItemCount() {
        return  images.getPhotos()==null?0:images.getPhotos().size();
    }

}
