package com.t_knight.and.capstone.ui.read;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.TopicTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC_ID = "topic_id";

    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.btn_prev) Button btnPrev;

    private ReadViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            TopicDescription topic = intent.getParcelableExtra(EXTRA_TOPIC_ID);
            ReadViewModel.ReadVMFactory factory =
                    new ReadViewModel.ReadVMFactory(getApplication(), topic);
            viewModel = ViewModelProviders.of(this, factory).get(ReadViewModel.class);
            showReadFragment();
            setupNavigationButtons();
        }
    }

    private void setupNavigationButtons() {
        viewModel.getNavigationButtonsState().observe(this, new Observer<Pair<Boolean, Boolean>>() {
            @Override public void onChanged(@Nullable Pair<Boolean, Boolean> state) {
                if (state != null) {
                    btnPrev.setEnabled(state.first);
                    btnNext.setEnabled(state.second);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewModel.navigateNextCard();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewModel.navigatePreviousCard();
            }
        });
    }

    private void showReadFragment() {
        ReadFragment fragment = new ReadFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flRead, fragment, ReadFragment.class.getSimpleName())
                .commit();
    }

}
