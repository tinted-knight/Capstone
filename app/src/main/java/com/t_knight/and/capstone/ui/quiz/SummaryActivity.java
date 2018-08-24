package com.t_knight.and.capstone.ui.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.t_knight.and.capstone.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends AppCompatActivity {

    public static final String KEY_QUIZ_RESULT = "quiz_result";

    private SummaryAdapter adapter;

    @BindView(R.id.rv_summary) RecyclerView rvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ButterKnife.bind(this);

        if (getIntent() != null) {
            List<QuizResult> values = getIntent().getParcelableArrayListExtra(KEY_QUIZ_RESULT);

            adapter = new SummaryAdapter();
            adapter.setData(values);

            rvSummary.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvSummary.setVerticalScrollBarEnabled(true);
            rvSummary.setAdapter(adapter);
        }

    }
}
