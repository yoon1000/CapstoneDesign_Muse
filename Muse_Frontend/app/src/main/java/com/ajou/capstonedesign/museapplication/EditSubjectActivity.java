package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class EditSubjectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ChooseMajorAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        recyclerView = (RecyclerView) findViewById(R.id.Majorrecyclerview);
        linearLayoutManager = new LinearLayoutManager(this);


        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, linearLayoutManager.getOrientation())
        );
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
