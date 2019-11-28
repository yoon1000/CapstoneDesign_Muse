package com.ajou.capstonedesign.museapplication;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseSubjectAdapter extends RecyclerView.Adapter<ChooseSubjectAdapter.ItemViewHolder2> {

    private static  final String TAG = "ChooseSubjectAdapter";
    private List<SubjectList> listData2;
    SparseBooleanArray itemStateArray= new SparseBooleanArray();


    String result0 = "";
    StringBuffer result = new StringBuffer();

    public ChooseSubjectAdapter(List<SubjectList> list) { this.listData2 = list; }

    @NonNull
    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutForItem, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list2, parent, false);
        return new ItemViewHolder2(view);

    }



    //체크박스에서 선택한 항목들이 Json형식에 맞게 내장 메모리에 들어간다.
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder2 holder, final int position) {

        holder.bind(position);
        /*holder.major.setText(listData2.get(position).getSubject());
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
                }
            }

        });
*/

    }

    @Override
    public int getItemCount() {
        return listData2.size();
    }

    void loadItems(List<SubjectList> tournaments){
        this.listData2 = tournaments;
        notifyDataSetChanged();
    }

    public class ItemViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckedTextView mCheckedTextView;

        private TextView major;
        private CheckBox check;

        public ItemViewHolder2(@NonNull View itemView) {
            super(itemView);

            mCheckedTextView = (CheckedTextView)itemView.findViewById(R.id.checked_text_view);
            itemView.setOnClickListener(this);
//            major = (TextView) itemView.findViewById(R.id.listname2);
//            check = (CheckBox) itemView.findViewById(R.id.majorsubcheck);
        }

        void bind(int position){
            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {
                mCheckedTextView.setChecked(false);}
            else {
                mCheckedTextView.setChecked(true);
            }
            mCheckedTextView.setText(String.valueOf(listData2.get(position).getPosition()));
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                mCheckedTextView.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            }
            else  {
                mCheckedTextView.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }

        }
    }

}