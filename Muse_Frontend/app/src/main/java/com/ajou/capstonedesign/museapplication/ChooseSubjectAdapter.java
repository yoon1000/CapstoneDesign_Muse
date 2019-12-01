package com.ajou.capstonedesign.museapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class ChooseSubjectAdapter extends RecyclerView.Adapter<ChooseSubjectAdapter.ItemViewHolder2> {

    private static  final String TAG = "ChooseSubjectAdapter";
    private List<SubjectList> listData2;

    String result0 = "";
    StringBuffer result = new StringBuffer();

    public ChooseSubjectAdapter(List<SubjectList> list) {
        this.listData2 = list;
        //
    }
    /*public ChooseSubjectAdapter(List subjectLists){
        this.resultmajor = subjectLists;
    }*/


    @NonNull
    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list2, parent, false);
        return new ItemViewHolder2(view);

    }

    //체크박스에서 선택한 항목들이 Json형식에 맞게 내장 메모리에 들어간다.
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder2 holder, final int position) {
        final int pos = position;
        final SubjectList subjectList = listData2.get(position);
        holder.check.setOnCheckedChangeListener(null);
        holder.check.setChecked(subjectList.isSelected());
        holder.check.setTag(listData2.get(position));
        holder.major.setText(listData2.get(position).getSubject());

        /*holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                subjectList.setSelected(isChecked);
                if(holder.check.isChecked()){
                    if(!result.toString().equals("")){
                        result.append(",");
                    }
                    result0 = '"'+holder.major.getText().toString()+'"';
                    result.append(result0);
                    *//*Intent intent = new Intent(buttonView.getContext(), AddSubjectActivity.class);
                    intent.putExtra("resultmajor", result.toString())*//*;

                    Intent intent = new Intent(buttonView.getContext(), ChooseSubjectActivity.class);
                    intent.putExtra("resultmajor", result.toString());
                    buttonView.getContext().startActivity(intent);
                    //SharedPreference.setAttribute(buttonView.getContext(),"resultmajor",result.toString());
                    //Log.d("taaaaaa",SharedPreference.getAttribute(buttonView.getContext(), "resultmajor"));
                }
            }
        });*/
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                SubjectList contact = (SubjectList) cb.getTag();
                contact.setSelected(cb.isChecked());
                listData2.get(pos).setSelected(cb.isChecked());

                if(holder.check.isChecked()){
                    if(!result.toString().equals("")){
                        result.append(",");
                    }
                    result0 = '"'+holder.major.getText().toString()+'"';
                    result.append(result0);
                    //SharedPreference.setAttribute(v.getContext(),"resultmajor",result.toString());
                    //Log.d("taaaaaa",SharedPreference.getAttribute(v.getContext(), "resultmajor"));
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
            text = (TextView)itemView.findViewById(R.id.textView5);
            major = (TextView) itemView.findViewById(R.id.listname2);
            check = (CheckBox) itemView.findViewById(R.id.majorsubcheck);
            //text = (TextView) itemView.findViewById(R.id.getcheck);
        }

    }

    public List<SubjectList> getSubject(){
        return listData2;
    }

}




/*
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
                    result0 = '"'+holder.major.getText().toString()+'"';
                    result.append(result0);
                    SharedPreference.setAttribute(v.getContext(),"resultmajor",result.toString());
                    Log.d("taaaaaa",SharedPreference.getAttribute(v.getContext(), "resultmajor"));
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
            check = (CheckBox) itemView.findViewById(R.id.majorsubcheck);
            //text = (TextView) itemView.findViewById(R.id.getcheck);
        }

    }

}*/