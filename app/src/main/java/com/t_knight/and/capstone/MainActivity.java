package com.t_knight.and.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.t_knight.and.capstone.job_dispatcher.TopicListJobService;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.TopicTitle;
import com.t_knight.and.capstone.ui.main.TopicDetailsFragment;
import com.t_knight.and.capstone.ui.main.TopicListAdapter;
import com.t_knight.and.capstone.ui.main.TopicListFragment;
import com.t_knight.and.capstone.ui.quiz.QuizActivity;
import com.t_knight.and.capstone.ui.read.ReadActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements TopicListAdapter.TopicListItemClick, TopicDetailsFragment.OnTopicDetailsInteractionListener {

    public static final String TAG = "TAGG";
        private static final int periodicity = (int) TimeUnit.HOURS.toSeconds(24); // 3 hours
    private static final int interval = (int) TimeUnit.MINUTES.toSeconds(15);
//    private static final int periodicity = (int) TimeUnit.SECONDS.toSeconds(15); // 3 hours
//    private static final int interval = (int) TimeUnit.SECONDS.toSeconds(15);

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        showTopicList();
        scheduleTopicListSync();
    }

    private void scheduleTopicListSync() {
        GooglePlayDriver googlePlayDriver = new GooglePlayDriver(this);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(googlePlayDriver);
        Job syncJob = dispatcher.newJobBuilder()
                .setService(TopicListJobService.class)
                .setTag(TopicListJobService.class.getSimpleName())
                .setConstraints(Constraint.DEVICE_CHARGING, Constraint.ON_ANY_NETWORK)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(periodicity, periodicity + interval))
                .build();
        try {
            dispatcher.mustSchedule(syncJob);
        } catch (FirebaseJobDispatcher.ScheduleFailedException e) {
            Timber.e(e);
        }
    }

    private void showTopicList() {
        TopicListFragment fragment = TopicListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flMain, fragment, TopicListFragment.class.getSimpleName())
                .commit();
    }

    @Override public void onTopicListItemClick(TopicEntity topic) {
        viewModel.setActiveTopic(topic.topicId);
        TopicDetailsFragment fragment = new TopicDetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.flMain, fragment, TopicDetailsFragment.class.getSimpleName())
                .commit();

        setupBtnClickListeners(fragment);
    }

    @Override public void onTopicListPinClick(TopicEntity topic) {
        Toast.makeText(this, "pin topic id = " + String.valueOf(topic.topicId), Toast.LENGTH_SHORT).show();
        viewModel.pinTopicToWidget(topic);
    }

    private void setupBtnClickListeners(TopicDetailsFragment fragment) {
        fragment.btnReadClick.observe(this, new Observer<TopicDescription>() {
            @Override public void onChanged(@Nullable TopicDescription value) {
                if (value != null) startReadActivity(value);
            }
        });

        fragment.btnQuizClick.observe(this, new Observer<Pair<Integer, Integer>>() {
            @Override public void onChanged(@Nullable Pair<Integer, Integer> value) {
                if (value != null) startQuizActivity(value.first, value.second);
            }
        });
    }

    private void startReadActivity(TopicDescription topicTitle) {
        Intent readIntent = new Intent(this, ReadActivity.class);
        readIntent.putExtra(ReadActivity.EXTRA_TOPIC_ID, topicTitle);
        startActivity(readIntent);
    }

    private void startQuizActivity(Integer id, Integer difficulty) {
        Intent quizIntent = new Intent(this, QuizActivity.class);
        quizIntent.putExtra(QuizActivity.EXTRA_TOPIC_ID, id);
        quizIntent.putExtra(QuizActivity.EXTRA_DIFFICULTY_LEVEL, difficulty);
        startActivity(quizIntent);
    }

    @Override public void onTopicDetailsFragmentInteraction(Uri uri) {
        Toast.makeText(this, "interaction", Toast.LENGTH_SHORT).show();
    }
}
