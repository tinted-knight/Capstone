package com.t_knight.and.capstone.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.t_knight.and.capstone.MainViewModel;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.TopicTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicDetailsFragment extends Fragment {

    private OnTopicDetailsInteractionListener listener;

    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.tv_description2) TextView tvDescription2;
    @BindView(R.id.btn_read) Button btnRead;
    @BindView(R.id.btn_quiz) Button btnQuiz;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private MainViewModel viewModel;
    private int topicId;

    public SingleLiveEvent<Integer> btnReadClick = new SingleLiveEvent<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_details, container, false);
        ButterKnife.bind(this, rootView);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                btnReadClick.setValue(topicId);
            }
        });

        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        registerObservers();
    }

    private void registerObservers() {
        viewModel.getActiveTopicDetails().observe(this, new Observer<TopicTitle>() {
            @Override public void onChanged(@Nullable TopicTitle topicTitle) {
                if (topicTitle != null) {
                    toolbar.setTitle(topicTitle.getTitleTo());
                    tvDescription.setText(topicTitle.getDescription());
                    tvDescription2.setText(topicTitle.getTitleFrom());
                    topicId = topicTitle.getId();
                }
            }
        });
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
