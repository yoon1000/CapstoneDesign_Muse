package com.ajou.capstonedesign.museapplication;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseMajorActivity extends AppCompatActivity {

    private ChooseMajorAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);

        init();

        getData();


    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerviewmajor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adaptor = new ChooseMajorAdaptor();
        recyclerView.setAdapter(adaptor);
    }

    private void getData(){

        //데이터베이스 가져오는 것 적용
        List<String> listTitle = Arrays.asList("소프트웨어학과", "사이버보안학과", "미디어학과", "전자공학과");

        for (int i = 0; i < listTitle.size(); i++) {
            ListData data = new ListData();
            data.setTitle(listTitle.get(i));

            adaptor.addItem(data);
        }

        adaptor.notifyDataSetChanged();
    }
}
