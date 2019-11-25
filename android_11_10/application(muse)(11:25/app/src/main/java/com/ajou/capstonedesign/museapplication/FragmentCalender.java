package com.ajou.capstonedesign.museapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentCalender extends Fragment {

    private Button AddTimetable;
    private TextView mon1, tue1, wed1, thr1, fri1,
            mon2, tue2, wed2, thr2, fri2,
            mon3, tue3, wed3, thr3, fri3;
    int dydlf = 2;
    int a = 49;
    int b = 51;



    public static FragmentCalender newInstance(){
        return new FragmentCalender();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calender, null);
        AddTimetable = (Button)view.findViewById(R.id.addtimetable);
        mon1 = (TextView)view.findViewById(R.id.Mon1);
        tue1 = (TextView)view.findViewById(R.id.Tue1);
        tue2 = (TextView)view.findViewById(R.id.Tue2);
        tue3 = (TextView)view.findViewById(R.id.Tue3);




        AddTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dydlf == 2){

                }


                /*((MainActivity)getActivity()).replaceFragment(FragmentCalender.newInstance());

                Intent intent = new Intent(v.getContext(), TimetableOptionPopup.class);
                startActivity(intent);*/

                //A1.setBackgroundColor(getResources().getColor(R.color.C4));}
            }

        });

        return view;

    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }*/
}