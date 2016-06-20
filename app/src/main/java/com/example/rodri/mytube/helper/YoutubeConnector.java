package com.example.rodri.mytube.helper;

import android.content.Context;
import android.util.Log;

import com.example.rodri.mytube.R;
import com.example.rodri.mytube.video.VideoItem;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 6/17/2016.
 */
public class YoutubeConnector {

    private YouTube youtube;
    private YouTube.Search.List query;
    public static final String KEY = "AIzaSyCXlfLgMdoGJa_rPGP-B0dR4HlcEnSrK2w";
    public static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    public YoutubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize:  " + e);
        }
    }

    public List<VideoItem> search(String keywords) {
        query.setQ(keywords);
        query.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
        try {
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<>();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            return items;

        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}
