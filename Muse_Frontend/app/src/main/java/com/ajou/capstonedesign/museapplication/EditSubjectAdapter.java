package com.ajou.capstonedesign.museapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditSubjectAdapter extends RecyclerView.Adapter<EditSubjectAdapter.ItemViewHolder> {

    private List<SubjectList> listData;

    String result0 = "";
    StringBuffer result = new StringBuffer();

    public EditSubjectAdapter(List<SubjectList> list) {
        this.listData = list;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list2, parent, false);
        return new ItemViewHolder(view);

    }

    //체크박스에서 선택한 항목들이 Json형식에 맞게 내장 메모리에 들어간다.
    @Override
    public void onBindViewHolder(@NonNull EditSubjectAdapter.ItemViewHolder holder, final int position) {
        final int pos = position;
        final SubjectList subjectList = listData.get(position);
        //holder.check.setChecked(true);//미리 다 체크 되어있음(?????)
        holder.check.setOnCheckedChangeListener(null);
        holder.check.setChecked(subjectList.isSelected());
        holder.check.setTag(listData.get(position));
        holder.major.setText(listData.get(position).getSubject());

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                SubjectList contact = (SubjectList) cb.getTag();
                contact.setSelected(cb.isChecked());
                listData.get(pos).setSelected(cb.isChecked());

                if(holder.check.isChecked()){
                    if(!result.toString().equals("")){
                        result.append(",");
                    }
                    result0 = '"'+holder.major.getText().toString()+'"';
                    result.append(result0);

                }
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
        private CheckBox check;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            major = (TextView) itemView.findViewById(R.id.listname2);
            check = (CheckBox) itemView.findViewById(R.id.majorsubcheck);
        }

    }

    public List<SubjectList> getSubject(){
        return listData;
    }


}
