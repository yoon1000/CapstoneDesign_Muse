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

    String result = "";

    public ChooseSubjectAdapter(List<SubjectList> list) { this.listData2 = list; }

    @NonNull
    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list2, parent, false);
        return new ItemViewHolder2(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder2 holder, final int position) {
        holder.major.setText(listData2.get(position).getSubject());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.isChecked()){
                    result +=  ","+  '\"'+holder.major.getText().toString()+'\"';
                    Log.d(TAG,result);
                    SharedPreference.setAttribute(v.getContext(),"result",result);
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