package com.t_knight.and.capstone.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.t_knight.and.capstone.MainViewModel;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.firebase.storage.GlideApp;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.helpers.QuizPair;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicDetailsFragment extends Fragment {

    private OnTopicDetailsInteractionListener listener;

    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.tv_description2) TextView tvDescription2;
    @BindView(R.id.btn_read) Button btnRead;
    @BindView(R.id.btn_quiz) Button btnQuiz;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.app_bar_image) ImageView ivCover;

    private MainViewModel viewModel;

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("qwe", viewModel.getActiveTopicId());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_details, container, false);
        ButterKnife.bind(this, rootView);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

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
                    setupBottomButtons(topicDescription);
                    String stringRef = "covers/" + topicDescription.getCoverUrl();
                    GlideApp.with(ivCover)
                            .load(viewModel.getStorageRef(stringRef))
                            .into(ivCover);
                }
            }
        });
    }

    private void setupBottomButtons(final TopicDescription topicDescription) {
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onBtnReadClick(topicDescription);
            }
        });
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onBtnQuizClick(
                        new QuizPair(topicDescription.getId(), Math.round(ratingBar.getRating())));
            }
        });
        btnRead.setEnabled(true);
        btnQuiz.setEnabled(true);
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
        void onBtnReadClick(TopicDescription topicDescription);

        void onBtnQuizClick(QuizPair quizPair);
    }
}
