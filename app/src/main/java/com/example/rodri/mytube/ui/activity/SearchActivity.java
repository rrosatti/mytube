package com.example.rodri.mytube.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rodri.mytube.R;
import com.example.rodri.mytube.helper.YoutubeConnector;
import com.example.rodri.mytube.ui.adapter.VideoItemAdapter;
import com.example.rodri.mytube.util.UtilMethods;
import com.example.rodri.mytube.video.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 6/18/2016.
 */
public class SearchActivity extends Activity {

    private EditText etSearchInput;
    private ListView listVideosFound;
    private ImageView imgSearchIcon;

    private Handler handler;
    private List<VideoItem> searchResults;
    private VideoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        initializeVariables();

        etSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });

        imgSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etSearchInput.getText().toString();
                if (!data.equals("")) {
                    searchOnYoutube(data);
                    UtilMethods.hideVirtualKeyboard(SearchActivity.this);
                }
            }
        });

    }

    public void initializeVariables() {
        etSearchInput = (EditText) findViewById(R.id.etSearchInput);
        listVideosFound = (ListView) findViewById(R.id.listVideosFound);
        imgSearchIcon = (ImageView) findViewById(R.id.imgSearchButton);

        handler = new Handler();
        searchResults = new ArrayList<>();
    }

    private void searchOnYoutube(final String keywords) {
        new Thread() {
            public void run() {
                YoutubeConnector youtubeConnector = new YoutubeConnector(SearchActivity.this);
                searchResults = youtubeConnector.search(keywords);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    public void updateVideosFound() {
        adapter = new VideoItemAdapter(this, 0, searchResults);
        listVideosFound.setAdapter(adapter);
    }
}
