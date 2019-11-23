package com.ajou.capstonedesign.museapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseSubject2Adapter extends RecyclerView.Adapter<ChooseSubject2Adapter.ItemViewHolder2> {

    private static  final String TAG = "ChooseSubjectAdapter";
    private List<SubjectList> listData2;

    String result = "";

    public ChooseSubject2Adapter(List<SubjectList> list) { this.listData2 = list; }

    @NonNull
    @Override
    public ChooseSubject2Adapter.ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list2, parent, false);
        return new ChooseSubject2Adapter.ItemViewHolder2(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChooseSubject2Adapter.ItemViewHolder2 holder, final int position) {
        holder.nonmajor.setText(listData2.get(position).getSubject());
        holder.check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check2.isChecked()){
                    result +=  ","+  '\"'+holder.nonmajor.getText().toString()+'\"';
                    Log.d(TAG,result);
                    SharedPreference.setAttribute(v.getContext(),"resultnonmajor",result);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return listData2.size();
    }

    public class ItemViewHolder2 extends RecyclerView.ViewHolder {

        private TextView nonmajor;
        private View mView;
        private TextView text;
        private CheckBox check2;

        public ItemViewHolder2(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            nonmajor = (TextView) itemView.findViewById(R.id.listname3);
            check2 = (CheckBox) itemView.findViewById(R.id.checkBox2);
            text = (TextView) itemView.findViewById(R.id.getcheck);
        }

    }

}