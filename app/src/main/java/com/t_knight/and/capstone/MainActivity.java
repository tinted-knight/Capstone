package com.t_knight.and.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.Topics;
import com.t_knight.and.capstone.model.Topics.Topic;
import com.t_knight.and.capstone.model.Topics.Topic.SingleCard;

import java.util.List;

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
        viewModel.getTopics().observe(this, topicObserver);
    }

    Observer<List<Topic>> topicObserver = new Observer<List<Topic>>() {
        @Override public void onChanged(@Nullable List<Topic> topics) {
            if (topics != null) {
                for (Topic topic : topics) {
                    Log.d(TAG, "onChanged: " + topic.getTitleFrom());
                }
            }
        }
    };

}
