package com.nexuslink.govermanage.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.govermanage.R;
import com.nexuslink.govermanage.ShowOneEventActivity;
import com.nexuslink.govermanage.bean.TaskRecv;

import java.util.List;

public class TaskUndoAdapter extends RecyclerView.Adapter<TaskUndoAdapter.ViewHolder> {

    private List<TaskRecv.DataBean>  activitys;

    public TaskUndoAdapter(List<TaskRecv.DataBean> activitys) {
        this.activitys = activitys;
    }

    @NonNull
    @Override
    public TaskUndoAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_undisposed_event, parent, false);
        final TaskUndoAdapter.ViewHolder holder = new TaskUndoAdapter.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                int id = activitys.get(position).getId();

                String status = "0";
                if (activitys.get(position).getMission_level().equals("0")) {
                    status = "1";
                } else if (activitys.get(position).getMission_level().equals("1")) {
                    status = "2";
                }

                Intent intent = new Intent(parent.getContext(), ShowOneEventActivity.class);
                intent.putExtra("id_id", id);
                intent.putExtra("status", status);
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskUndoAdapter.ViewHolder holder, int position) {
        Log.d("ddddddddddddddd", activitys.get(position).toString());
        holder.title.setText("标题：" + activitys.get(position).getMission_title());
        switch (Integer.valueOf(activitys.get(position).getMission_type())) {
            case 0:
                holder.source.setText("事件类型：群体活动");
                break;
            case 1:
                holder.source.setText("事件类型：宣传活动");
                break;
            case 2:
                holder.source.setText("事件类型：寻访活动");
                break;
            case 3:
                holder.source.setText("事件类型：工作例会");
                break;
            case 4:
                holder.source.setText("事件类型：通知");
                break;
        }

        holder.type.setText("任务发布主任id：" + activitys.get(position).getMission_from());

        String str = "提交时间：" + activitys.get(position).getCreated_at();
        holder.time.setText(str);
    }

    @Override
    public int getItemCount() {
        return activitys.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView source;
        TextView type;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_event);
            source = itemView.findViewById(R.id.source_event);
            type = itemView.findViewById(R.id.type_event);
            time = itemView.findViewById(R.id.time_event);
        }
    }

}
