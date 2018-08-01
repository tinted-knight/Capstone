package com.t_knight.and.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.t_knight.and.capstone.main.TopicListAdapter;
import com.t_knight.and.capstone.main.TopicListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements TopicListAdapter.TopicListItemClick {

    public static final String TAG = "TAGG";

    @BindView(R.id.flMain) FrameLayout flMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        showTopicList();
    }

    private void showTopicList() {
        TopicListFragment topicListFragment = TopicListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flMain, topicListFragment, TopicListFragment.class.getSimpleName())
                .commit();
    }

    @Override public void onTopicListItemClick(int id) {
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }
}
