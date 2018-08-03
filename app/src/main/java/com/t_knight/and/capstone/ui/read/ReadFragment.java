package com.t_knight.and.capstone.ui.read;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.SingleCard;
import com.t_knight.and.capstone.model.Topic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends Fragment {

    @BindView(R.id.tv_to) TextView tvTo;
    @BindView(R.id.tv_from) TextView tvFrom;

    private ReadViewModel viewModel;

    public ReadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_read, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ReadViewModel.class);
        registerObservers();
    }

    private void registerObservers() {
        viewModel.getTopicContent().observe(this, new Observer<Topic>() {
            @Override public void onChanged(@Nullable Topic topic) {
                if (topic != null) {
                    viewModel.showFirstCard();
//                    tvTo.setText(topic.getCardContent().get(1).getTo());
//                    tvFrom.setText(topic.getCardContent().get(1).getFrom());
                }
            }
        });

        viewModel.getCardContent().observe(this, new Observer<SingleCard>() {
            @Override public void onChanged(@Nullable SingleCard singleCard) {
                if (singleCard != null) {
                    tvTo.setText(singleCard.getTo());
                    tvFrom.setText(singleCard.getFrom());
                }
            }
        });
    }
}
