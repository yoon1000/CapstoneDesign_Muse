package com.ajou.capstonedesign.museapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentHome extends Fragment {

    TextView idtext;
    Button button;

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);
        button = (Button)view.findViewById(R.id.add);
        idtext = (TextView)view.findViewById(R.id.userid);

        //로그인을 통해 내장메모리에서 받아온 id와 major값을 화면에 버튼과 텍스트뷰에 띄워준다.
        button.setText(SharedPreference.getAttribute(view.getContext(),"major"));
        idtext.setText(SharedPreference.getAttribute(view.getContext(), "id"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fragment에서 activity로 넘기려면 이렇게 써야함
                Intent intent1 = new Intent(getActivity(), HomeAdd.class);
                startActivity(intent1);
            }
        });
        return view;
    }



}



