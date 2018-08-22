package com.t_knight.and.capstone.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.firebase.storage.GlideApp;
import com.t_knight.and.capstone.local_db.TopicEntity;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicListViewHolder> {

    private List<TopicEntity> data;
    private final TopicListItemClick listener;

    private final StorageReference reference = FirebaseStorage.getInstance().getReference();

    TopicListAdapter(TopicListItemClick listener) {
        this.listener = listener;
    }

    @NonNull @Override
    public TopicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_topic_list, parent, false);

        return new TopicListViewHolder(itemView);
    }

    public void setData(List<TopicEntity> newData) {
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
        TopicEntity item = data.get(position);
        holder.bind(item);
        holder.itemView.setTag(item.topicId);
    }

    public interface TopicListItemClick {
        void onTopicListItemClick(TopicEntity topic, View view);

        void onTopicListPinClick(TopicEntity topic);
    }

    class TopicListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tv_description) TextView tvDescription;
        @BindView(R.id.ib_pin) ImageButton ibPin;
        @BindView(R.id.iv_cover) ImageView ivCover;

        @BindDrawable(R.drawable.ic_bookmark_border_24dp) Drawable drawableNotPinned;
        @BindDrawable(R.drawable.ic_bookmark_24dp) Drawable drawablePinned;

        TopicListViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null)
                        listener.onTopicListItemClick(data.get(getAdapterPosition()), ivCover);
                }
            });
            ibPin.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null)
                        listener.onTopicListPinClick(data.get(getAdapterPosition()));
                }
            });
        }

        void bind(TopicEntity topic) {
            tvTitle.setText(topic.titleFrom);
            tvDescription.setText(topic.titleTo);
            if (topic.pinned) {
                ibPin.setImageDrawable(drawablePinned);
            } else {
                ibPin.setImageDrawable(drawableNotPinned);
            }
            String stringRef = "covers/" + topic.coverUrl;
            GlideApp.with(ivCover)
                    .load(reference.child(stringRef))
                    .into(ivCover);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivCover.setTransitionName("iv_trans_name" + String.valueOf(topic.topicId));
            }
        }
    }
}
