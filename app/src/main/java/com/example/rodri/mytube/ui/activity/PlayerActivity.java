package com.example.rodri.mytube.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rodri.mytube.R;
import com.example.rodri.mytube.helper.YoutubeConnector;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by rodri on 6/19/2016.
 */
public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.player_activity);

        playerView = (YouTubePlayerView) findViewById(R.id.playerView);
        playerView.initialize(YoutubeConnector.KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        if (!restored) {
            youTubePlayer.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }
}
