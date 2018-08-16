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

import com.t_knight.and.capstone.MainViewModel;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.model.FireContent;
import com.t_knight.and.capstone.model.TopicTitle;
import com.t_knight.and.capstone.model.helpers.AppPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MainViewModel viewModel;
    private TopicListAdapter adapter;

    @BindView(R.id.rvTopicList) RecyclerView rvTopicList;

    public TopicListFragment() {
        // Required empty public constructor
    }

    public static TopicListFragment newInstance() {
        TopicListFragment fragment = new TopicListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "");
        args.putString(ARG_PARAM2, "");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        viewModel.loadFromNetwork().observe(this, new Observer<FireContent>() {
            @Override public void onChanged(@Nullable FireContent content) {
                if (content != null){
                    AppPreferences prefs = new AppPreferences(getActivity());
                    if (prefs.isFirstStart() || prefs.isNewerVersion(content.getVersion())) {
                        viewModel.fillLocalDatabase();
                        prefs.setFirstStartFalse();
                    }
                }
            }
        });

//        viewModel.loadFromNetwork().observe(this, new Observer<List<TopicTitle>>() {
//            @Override public void onChanged(@Nullable List<TopicTitle> topicTitles) {
////                adapter.setData(topicTitles);
//                AppPreferences firstStart = new AppPreferences(getActivity());
//                if (firstStart.isFirstStart()) {
//                    viewModel.fillLocalDatabase();
//                    firstStart.setFirstStartFalse();
//                }
//            }
//        });

        viewModel.getAllTopics().observe(this, new Observer<List<TopicEntity>>() {
            @Override public void onChanged(@Nullable List<TopicEntity> data) {
                if (data != null)
                    adapter.setData(data);
            }
        });
    }
}
