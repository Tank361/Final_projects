package com.example.final_projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context dData;
    private ArrayList<Post> mData ;

    public MyAdapter(Context context ,ArrayList<Post> data) {
        this.mData = data;
        this.dData = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.day = (TextView) view.findViewById(R.id.day);
        holder.description = (TextView) view.findViewById(R.id.description);
        holder.high = (TextView) view.findViewById(R.id.high);
        holder.low = (TextView) view.findViewById(R.id.low);
        holder.precip = (TextView) view.findViewById(R.id.precip);
        holder.humidity = (TextView) view.findViewById(R.id.humidity);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mData.get(position);

        // 設置day要顯示的內容
        holder.day.setText(post.day);
        // 設置description要顯示的內容
        holder.description.setText(post.description);
        // 設置high要顯示的內容
        holder.high.setText(post.high);
        // 設置low要顯示的內容
        holder.low.setText(post.low);
        // 設置precip要顯示的內容
        holder.precip.setText(post.precip);
        // 設置humidity要顯示的內容
        holder.humidity.setText(post.humidity);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        // 宣告元件
        public TextView day;
        public TextView description;
        public TextView high;
        public TextView low;
        public TextView precip;
        public TextView humidity;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

