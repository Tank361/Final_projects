package com.example.final_projects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Post> mData ;

    public MyAdapter(ArrayList<Post> data) {
        this.mData = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //連接各個元件
        holder.time = (TextView) view.findViewById(R.id.time);
        holder.description = (TextView) view.findViewById(R.id.temp);
        holder.humidity = (TextView) view.findViewById(R.id.humidity);
        holder.temp = (TextView) view.findViewById(R.id.description);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mData.get(position);

        // 設置time要顯示的內容
        holder.time.setText(post.time);
        // 設置description要顯示的內容
        holder.description.setText(post.description);
        // 設置temp要顯示的內容
        holder.temp.setText(post.temp);
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
        public TextView time;
        public TextView description;
        public TextView temp;
        public TextView humidity;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

