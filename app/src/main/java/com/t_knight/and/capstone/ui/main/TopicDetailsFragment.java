package com.t_knight.and.capstone.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.t_knight.and.capstone.MainViewModel;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.TopicTitle;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicDetailsFragment extends Fragment {

    private OnTopicDetailsInteractionListener listener;

    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.tv_description2) TextView tvDescription2;
    @BindView(R.id.btn_read) Button btnRead;
    @BindView(R.id.btn_quiz) Button btnQuiz;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.ratingBar) RatingBar ratingBar;

    private static final int DIF_LEVEL_1 = 1;
    private static final int DIF_LEVEL_2 = 2;
    private static final int DIF_LEVEL_3 = 3;
    private static final int DIF_LEVEL_4 = 4;
    private int difficultyLevel;
    private MainViewModel viewModel;

    public SingleLiveEvent<TopicDescription> btnReadClick = new SingleLiveEvent<>();
    public SingleLiveEvent<Pair<Integer, Integer>> btnQuizClick = new SingleLiveEvent<>();

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("qwe", viewModel.getActiveTopicId());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_details, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        if (savedInstanceState != null)
            viewModel.setActiveTopic(savedInstanceState.getInt("qwe"));
        registerObservers();
    }

    private void registerObservers() {
        viewModel.getActiveTopicDetails().observe(this, new Observer<TopicDescription>() {
            @Override public void onChanged(@Nullable TopicDescription topicDescription) {
                if (topicDescription != null) {
                    toolbar.setTitle(topicDescription.getTitleTo());
                    tvDescription.setText(topicDescription.getDescription());
                    tvDescription2.setText(topicDescription.getTitleFrom());
                    btnReadClick(topicDescription);
                }
            }
        });
//        viewModel.getActiveTopicDetails().observe(this, new Observer<TopicTitle>() {
//            @Override public void onChanged(@Nullable TopicTitle topicTitle) {
//                if (topicTitle != null) {
//                    toolbar.setTitle(topicTitle.getTitleTo());
//                    tvDescription.setText(topicTitle.getDescription());
//                    tvDescription2.setText(topicTitle.getTitleFrom());
//                    btnReadClick(topicTitle);
//                }
//            }
//        });
    }

    private void btnReadClick(final TopicDescription topic) {
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                btnReadClick.setValue(topic);
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//                btnQuizClick.setValue(topic.getId());
                btnQuizClick.setValue(new Pair<>(topic.getId(), Math.round(ratingBar.getRating())));
            }
        });
    }

    public void ratingBarClick(View view) {
        if (view.getId() == R.id.ratingBar) {
            difficultyLevel = Math.round(ratingBar.getRating());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTopicDetailsInteractionListener) {
            listener = (OnTopicDetailsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTopicDetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnTopicDetailsInteractionListener {
        void onTopicDetailsFragmentInteraction(Uri uri);
    }
}
