package com.t_knight.and.capstone.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.model.TopicList;
import com.t_knight.and.capstone.model.helpers.AppPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicListFragment extends Fragment {

    private MainViewModel viewModel;
    private TopicListAdapter adapter;

    @BindView(R.id.rvTopicList) RecyclerView rvTopicList;

    public TopicListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_list, container, false);

        ButterKnife.bind(this, rootView);
        recyclerViewPrepare();

        return rootView;
    }

    private void recyclerViewPrepare() {
        adapter = new TopicListAdapter((TopicListAdapter.TopicListItemClick) getActivity());
        rvTopicList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvTopicList.setVerticalScrollBarEnabled(true);
        rvTopicList.setAdapter(adapter);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        registerObservers();
    }

    private void registerObservers() {
        viewModel.loadFromNetwork().observe(this, new Observer<TopicList>() {
            @Override public void onChanged(@Nullable TopicList content) {
                if (content != null){
                    AppPreferences prefs = new AppPreferences(getActivity());
                    if (prefs.isFirstStart() || prefs.isNewerVersion(content.getVersion())) {
                        viewModel.fillLocalDatabase();
                        prefs.setFirstStartFalse();
                        prefs.setVersion(content.getVersion());
                    }
                }
            }
        });

        viewModel.getAllTopics().observe(this, new Observer<List<TopicEntity>>() {
            @Override public void onChanged(@Nullable List<TopicEntity> data) {
                if (data != null)
                    adapter.setData(data);
            }
        });
    }
}
