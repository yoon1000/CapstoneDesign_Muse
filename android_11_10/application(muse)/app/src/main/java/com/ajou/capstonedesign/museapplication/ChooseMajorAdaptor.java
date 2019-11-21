package com.ajou.capstonedesign.museapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseMajorAdaptor extends RecyclerView.Adapter<ChooseMajorAdaptor.ItemViewHolder> {

    private ArrayList<ListData> listData = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_school_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseMajorAdaptor.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(ListData Data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(Data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView major;

        ItemViewHolder(View itemView) {
            super(itemView);

            major = itemView.findViewById(R.id.schoolname);
        }

        void onBind(ListData data) {
            major.setText(data.getTitle());
        }
    }
}
