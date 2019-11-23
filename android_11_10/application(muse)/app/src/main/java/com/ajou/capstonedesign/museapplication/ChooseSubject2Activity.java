package com.ajou.capstonedesign.museapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseSubject2Activity extends AppCompatActivity {

    private static  final String TAG = "ChooseSubject2Activity";
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager;
    private ChooseSubjectAdapter recyclerAdapter;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject2);

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview3);
        btn = (Button) findViewById(R.id.btn2);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView2.setHasFixedSize(true);

        recyclerView2.addItemDecoration(
                new DividerItemDecoration(this, linearLayoutManager.getOrientation())
        );
        recyclerView2.setLayoutManager(linearLayoutManager);

        JsonObject majorList2 = new JsonObject();

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> nonmajorlist = retrofitCommunication.nonmajorlist();

        nonmajorlist.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                JsonObject res = response.body();
////
                Log.d("Received", res.toString());

                List<SubjectList> subjectList = gson.fromJson(res.get("result"), new TypeToken<List<SubjectList>>(){}.getType());

                recyclerAdapter = new ChooseSubjectAdapter(subjectList);
                recyclerView2.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSubjectActivity.class);
                startActivity(intent);
            }

        });

    }
}
