package com.t_knight.and.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.t_knight.and.capstone.model.TopicTitle;
import com.t_knight.and.capstone.ui.main.TopicDetailsFragment;
import com.t_knight.and.capstone.ui.main.TopicListAdapter;
import com.t_knight.and.capstone.ui.main.TopicListFragment;
import com.t_knight.and.capstone.ui.read.ReadActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements TopicListAdapter.TopicListItemClick, TopicDetailsFragment.OnTopicDetailsInteractionListener {

    public static final String TAG = "TAGG";

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        showTopicList();
    }

    private void showTopicList() {
        TopicListFragment fragment = TopicListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flMain, fragment, TopicListFragment.class.getSimpleName())
                .commit();
    }

    @Override public void onTopicListItemClick(TopicTitle topicTitle) {
        viewModel.setActiveTopic(topicTitle.getId());
        TopicDetailsFragment fragment = new TopicDetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.flMain, fragment, TopicDetailsFragment.class.getSimpleName())
                .commit();

        fragment.btnReadClick.observe(this, new Observer<Integer>() {
            @Override public void onChanged(@Nullable Integer topicId) {
                Intent readIntent = new Intent(MainActivity.this, ReadActivity.class);
                readIntent.putExtra(ReadActivity.EXTRA_TOPIC_ID, topicId);
                startActivity(readIntent);
//                Toast.makeText(MainActivity.this, "read click: " + String.valueOf(topicId), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override public void onTopicDetailsFragmentInteraction(Uri uri) {
        Toast.makeText(this, "interaction", Toast.LENGTH_SHORT).show();
    }
}
