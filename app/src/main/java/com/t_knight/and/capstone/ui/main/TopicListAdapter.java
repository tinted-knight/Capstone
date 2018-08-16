package com.t_knight.and.capstone.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.TopicTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicListViewHolder> {

    private List<TopicTitle> data;
    private final TopicListItemClick listener;

    TopicListAdapter(TopicListItemClick listener) {
        this.listener = listener;
    }

    @NonNull @Override
    public TopicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_topic_list, parent, false);

        return new TopicListViewHolder(itemView);
    }

    public void setData(List<TopicTitle> newData) {
        if (newData == null) return;
        data = newData;
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TopicListViewHolder holder, int position) {
        TopicTitle item = data.get(position);
        holder.bind(item);
        holder.itemView.setTag(item.getId());
    }

    public interface TopicListItemClick {
        void onTopicListItemClick(TopicTitle topicTitle);
    }

    class TopicListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tv_description) TextView tvDescription;

        TopicListViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null)
                        listener.onTopicListItemClick(data.get(getAdapterPosition()));
                }
            });
        }

        void bind(TopicTitle topicTitle) {
            tvTitle.setText(topicTitle.getTitleFrom());
            tvDescription.setText(topicTitle.getTitleTo());
        }
    }
}
