package com.nexuslink.govermanage.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexuslink.govermanage.bean.EventModel;
import com.nexuslink.govermanage.R;
import com.nexuslink.govermanage.ShowOneWorkActivity;

import java.util.List;

public class WorkShowAdapter extends RecyclerView.Adapter<WorkShowAdapter.ViewHolder> {

    private List<EventModel> activitys;

    public WorkShowAdapter(List<EventModel> activitys) {
        this.activitys = activitys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_undisposed_event, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                int id = activitys.get(position).getId();

                String status = "0";
                if (activitys.get(position).getStatus().equals("0")) {
                    status = "1";
                } else if (activitys.get(position).getStatus().equals("1")) {
                    status = "2";
                }

                Intent intent = new Intent(parent.getContext(), ShowOneWorkActivity.class);
                intent.putExtra("id_id", id);
                intent.putExtra("status", status);
                parent.getContext().startActivity(intent);


                //*******************************************************************


                //*******************************************************************

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(activitys.get(position).getTitle());
        switch (activitys.get(position).getType()) {
            case 1:
                holder.source.setText("工作类型：巡查");
                break;
            case 2:
                holder.source.setText("工作类型：走访");
                break;
            case 3:
                holder.source.setText("工作类型：宣传");
                break;
            case 4:
                holder.source.setText("工作类型：处理");
                break;
            case 5:
                holder.source.setText("工作类型：重点人员寻访");
                break;
        }

        holder.type.setText("工作内容：" + activitys.get(position).getContent());

        String str = "提交时间：" + activitys.get(position).getTime();
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
