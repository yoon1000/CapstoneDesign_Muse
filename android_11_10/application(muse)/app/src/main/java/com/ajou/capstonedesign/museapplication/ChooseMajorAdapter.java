package com.ajou.capstonedesign.museapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseMajorAdapter extends RecyclerView.Adapter<ChooseMajorAdapter.ItemViewHolder> {

    private List<MajorList> listData;

    public ChooseMajorAdapter(List<MajorList> list) { this.listData = list; }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        holder.major.setText(listData.get(position).getMajor());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), Register_Activity.class);
                Intent intent = new Intent("majorToregister");
                intent.putExtra("major",listData.get(position).getMajor());
                LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                ((ChooseMajorActivity)v.getContext()).finish();
            }

        });
    }



    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView major;
        private View mView;
        private TextView text;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            major = (TextView) itemView.findViewById(R.id.listname);
            text = (TextView)itemView.findViewById(R.id.selected);
        }

        void onBind(ListData data) {
            major.setText(data.getTitle());
        }
    }




}