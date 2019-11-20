package com.ajou.capstonedesign.museapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseSchoolAdaptor extends RecyclerView.Adapter<ChooseSchoolAdaptor.SchoolViewHolder> {

    private ArrayList<String> mData = null ;

    public class SchoolViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        SchoolViewHolder( View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.schoolname) ;
            button = itemView.findViewById(R.id.addschool);
        }
    }

    ChooseSchoolAdaptor(ArrayList<String> list){
        mData = list;
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.view_school_list, parent, false) ;
        ChooseSchoolAdaptor.SchoolViewHolder vh = new ChooseSchoolAdaptor.SchoolViewHolder(view) ;

        return vh ;
    }


    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.textView.setText(text) ;
        holder.button.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}