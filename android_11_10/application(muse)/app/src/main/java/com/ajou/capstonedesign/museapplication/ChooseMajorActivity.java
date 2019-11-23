package com.ajou.capstonedesign.museapplication;

import android.os.Bundle;
import android.util.Log;

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

public class ChooseMajorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ChooseMajorAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);


        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, linearLayoutManager.getOrientation())
        );
        recyclerView.setLayoutManager(linearLayoutManager);

        JsonObject majorlist = new JsonObject();

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> regisetermajor = retrofitCommunication.regisetermajorlist();

        regisetermajor.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                JsonObject res = response.body();

                Log.d("Received", res.toString());

                List<MajorList> majorList = gson.fromJson(res.get("result"), new TypeToken<List<MajorList>>(){}.getType());

                recyclerAdapter = new ChooseMajorAdapter(majorList);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}