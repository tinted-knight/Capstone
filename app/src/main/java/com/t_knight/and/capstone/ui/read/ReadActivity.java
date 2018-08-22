package com.t_knight.and.capstone.ui.read;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicDescription;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC_ID = "topic_id";

    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.btn_prev) Button btnPrev;
    @BindView(R.id.toolbar2) Toolbar toolbar;

    private ReadViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                TopicDescription topic = intent.getParcelableExtra(EXTRA_TOPIC_ID);
                ReadViewModel.ReadVMFactory factory =
                        new ReadViewModel.ReadVMFactory(getApplication(), topic);
                viewModel = ViewModelProviders.of(this, factory).get(ReadViewModel.class);
                registerObservers();
                showReadFragment();
                setupNavigationButtons();
            }
        }
    }

    private void registerObservers() {
        viewModel.getTopicContent().observe(this, new Observer<Topic>() {
            @Override public void onChanged(@Nullable Topic topic) {
                if (topic != null)
                    toolbar.setTitle(topic.getTitleTo());
            }
        });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
