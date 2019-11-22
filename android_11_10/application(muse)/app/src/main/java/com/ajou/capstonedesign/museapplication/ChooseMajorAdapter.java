package com.ajou.capstonedesign.museapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseMajorAdapter extends RecyclerView.Adapter<ChooseMajorAdapter.ItemViewHolder> {

    private List<MajorList> listData;
    public ChooseMajorAdapter(List<MajorList> list) { this.listData = list; }

    private MyItemClickListner listener;

    public void setOnItemClickListener(MyItemClickListner listener){
        this.listener = listener;
    }

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
            major = itemView.findViewById(R.id.listname);
            text = itemView.findViewById(R.id.selected);



        }

        void onBind(ListData data) {
            major.setText(data.getTitle());
        }
    }
}
