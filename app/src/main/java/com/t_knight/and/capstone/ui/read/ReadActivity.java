package com.t_knight.and.capstone.ui.read;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.t_knight.and.capstone.R;

import butterknife.ButterKnife;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC_ID = "topic_id";

    private ReadViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Integer topicId = intent.getIntExtra(EXTRA_TOPIC_ID, 0);
            ReadViewModel.ReadVMFactory factory =
                    new ReadViewModel.ReadVMFactory(getApplication(), topicId);
            viewModel = ViewModelProviders.of(this, factory).get(ReadViewModel.class);
        }

        showReadFragment();
    }

    private void showReadFragment() {
        ReadFragment fragment = new ReadFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flRead, fragment, ReadFragment.class.getSimpleName())
                .commit();
    }

}
