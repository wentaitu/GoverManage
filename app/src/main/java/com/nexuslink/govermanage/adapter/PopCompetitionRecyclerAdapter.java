package com.nexuslink.govermanage.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nexuslink.govermanage.BusinessPlatform.WebviewbusinessActivity;
import com.nexuslink.govermanage.R;


/**
 * Created by wentai on 18-12-11.
 */

public class PopCompetitionRecyclerAdapter extends RecyclerView.Adapter<PopCompetitionRecyclerAdapter.ViewHolder> {

    private int[] activitys;

    public PopCompetitionRecyclerAdapter(int[] activitys) {
        this.activitys = activitys;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pop_competition, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "网易新闻");
                intent.putExtra("bus_url", "http://3g.163.com/touch/news/");
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.activity.setImageResource(activitys[position]);
    }

    @Override
    public int getItemCount() {
        return activitys.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView activity;

        public ViewHolder(View itemView) {
            super(itemView);
            activity = itemView.findViewById(R.id.item_pop_competition);
        }
    }
}
