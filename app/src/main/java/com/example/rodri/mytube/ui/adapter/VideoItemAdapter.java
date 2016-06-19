package com.example.rodri.mytube.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.rodri.mytube.R;
import com.example.rodri.mytube.video.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rodri on 6/18/2016.
 */
public class VideoItemAdapter extends ArrayAdapter<VideoItem> {

    private Activity activity;
    private List<VideoItem> videos;
    private LayoutInflater inflater = null;

    public VideoItemAdapter(Activity activity, int textViewResourceId, List<VideoItem> videos) {
        super (activity, textViewResourceId, videos);
        try {
            this.activity = activity;
            this.videos = videos;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    public static class ViewHolder {
        public ImageView displayThumbnail;
        public TextView displayVideoTitle;
        public TextView displayVideoDescription;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.video_item, null);

            holder.displayThumbnail = (ImageView) v.findViewById(R.id.imgVideoThumbnail);
            holder.displayVideoTitle = (TextView) v.findViewById(R.id.txtVideoTitle);
            holder.displayVideoDescription = (TextView) v.findViewById(R.id.txtVideoDecription);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Picasso.with(activity).load(videos.get(position).getThumbnailURL()).into(holder.displayThumbnail);
        holder.displayVideoTitle.setText(videos.get(position).getTitle());
        holder.displayVideoDescription.setText(videos.get(position).getDescription());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PlayerActivity.class);
                intent.putExtra("VIDEO_ID", videos.get(position).getId());
                activity.startActivity(intent);
            }
        });

        return v;

    }


}