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

public class ChooseSubjectAdapter extends RecyclerView.Adapter<ChooseSubjectAdapter.ItemViewHolder2> {

    private static  final String TAG = "ChooseSubjectAdapter";
    private List<SubjectList> listData2;

    String result0 = "";
    StringBuffer result = new StringBuffer();

    public ChooseSubjectAdapter(List<SubjectList> list) { this.listData2 = list; }

    @NonNull
    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list2, parent, false);
        return new ItemViewHolder2(view);

    }

    //체크박스에서 선택한 항목들이 Json형식에 맞게 내장 메모리에 들어간다.
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder2 holder, final int position) {
        holder.major.setText(listData2.get(position).getSubject());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.isChecked()){
                    if(!result.toString().equals("")){
                        result.append(",");
                    }
                    result0 =  '\"'+holder.major.getText().toString()+'\"';
                    result.append(result0);
                    SharedPreference.setAttribute(v.getContext(),"resultmajor",result.toString());
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return listData2.size();
    }

    public class ItemViewHolder2 extends RecyclerView.ViewHolder {

        private TextView major;
        private View mView;
        private TextView text;
        private CheckBox check;

        public ItemViewHolder2(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            major = (TextView) itemView.findViewById(R.id.listname2);
            check = (CheckBox) itemView.findViewById(R.id.checkBox);
            text = (TextView) itemView.findViewById(R.id.getcheck);
        }

    }

}