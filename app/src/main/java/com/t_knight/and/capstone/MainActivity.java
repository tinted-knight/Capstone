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

//    DatabaseReference reference;

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
//        initFirebaseDatabse();
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

//    Observer<Topic> topicObserver = new Observer<Topic>() {
//        @Override public void onChanged(@Nullable Topic topic) {
//            if (topic != null) {
//                tvTitle.setText(topic.getTitle_dest());
//                tvTitleTranslation.setText(topic.getTitle_source());
//                Pair<String, String> text = composeText(topic.getText());
//                tvTextSrc.setText(text.first);
//                tvTextTranslation.setText(text.second);
//            }
//        }
//
//        private Pair<String, String> composeText(List<Topic.CardsContent> text) {
//            StringBuilder src = new StringBuilder();
//            StringBuilder dst = new StringBuilder();
//            for (Topic.CardsContent cardsContent : text) {
//                src.append(cardsContent.getSrc());
//                dst.append(cardsContent.getDst());
//            }
//            return new Pair<>(src.toString(), dst.toString());
//        }
//    };

}
