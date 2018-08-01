package com.t_knight.and.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.t_knight.and.capstone.model.TopicDescList;
import com.t_knight.and.capstone.model.TopicList;
import com.t_knight.and.capstone.model.Topics;
import com.t_knight.and.capstone.model.Topics.Topic;
import com.t_knight.and.capstone.model.Topics.TopicTitle;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAGG";

    TextView tvTitle;
    TextView tvTitleTranslation;
    TextView tvTextSrc;
    TextView tvTextTranslation;

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvTitleTranslation = findViewById(R.id.tvTtitleTranslation);
        tvTextSrc = findViewById(R.id.tvTextSrc);
        tvTextTranslation = findViewById(R.id.tvTextTranslation);

        viewModel = getViewModel();
        registerLiveDataObservers();
    }

    private MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void registerLiveDataObservers() {
//        viewModel.getAllTopicsContent().observe(this, new Observer<TopicList>() {
//            @Override public void onChanged(@Nullable TopicList topics) {
//                if (topics != null) {
//                    for (Topic topic : topics) {
//                        Log.d(TAG, "onChanged: " + topic.getTitleFrom());
//                    }
//                }
//            }
//        });
        viewModel.getTopicTitles().observe(this, new Observer<TopicDescList>() {
            @Override public void onChanged(@Nullable TopicDescList topicTitles) {
                if (topicTitles != null)
                    for (TopicTitle topicTitle : topicTitles) {
                        Log.d(TAG, "onChanged: " + topicTitle.getDescription());
                    }
            }
        });
    }

}
