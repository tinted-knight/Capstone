package com.t_knight.and.capstone.ui.quiz;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t_knight.and.capstone.R;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private List<QuizResult> data;

    @NonNull @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_summary, parent, false);

        return new SummaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    public void setData(List<QuizResult> newData) {
        if (newData == null) return;
        data = newData;
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    class SummaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout) ConstraintLayout row;
        @BindView(R.id.tv_word) TextView tvWord;
        @BindView(R.id.tv_translation) TextView tvTranslation;

        @BindColor(R.color.colorSummaryRight) int COLOR_WRIGHT;
        @BindColor(R.color.colorSummaryWrong) int COLOR_WRONG;

        SummaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(QuizResult value) {
            tvWord.setText(value.correctAnswer);
            tvTranslation.setText(value.translation);
            if (value.isCorrect)
                row.setBackgroundColor(COLOR_WRIGHT);
            else
                row.setBackgroundColor(COLOR_WRONG);
        }
    }

}
