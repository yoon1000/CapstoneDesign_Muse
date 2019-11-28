package com.ajou.capstonedesign.museapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseSubjectActivity extends AppCompatActivity {

    //private static  final String TAG = "ChooseMajorActivity2";
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager;
    private ChooseSubjectAdapter recyclerAdapter;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);

        recyclerView2 = (RecyclerView) findViewById(R.id.majorsubjectlist);
        ChooseSubjectAdapter adapter = new ChooseSubjectAdapter();
        btn = (Button) findViewById(R.id.completebtn);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView2.setHasFixedSize(true);

        recyclerView2.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setAdapter(adapter);

        //id와 전공을 보내주면 사용자의 안들은 전공과목들을 불러와준다.
        JsonObject logindata = new JsonObject();
        logindata.addProperty("id",SharedPreference.getAttribute(ChooseSubjectActivity.this, "id"));
        logindata.addProperty("major",SharedPreference.getAttribute(ChooseSubjectActivity.this, "major"));

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> majorlist = retrofitCommunication.majorlist(logindata);

        majorlist.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                JsonObject res = response.body();

                Log.d("Received", res.toString());

                List<SubjectList> subjectList = gson.fromJson(res.get("result"), new TypeToken<List<SubjectList>>(){}.getType());

                //recyclerAdapter = new ChooseSubjectAdapter(subjectList);
                //recyclerView2.setAdapter(recyclerAdapter);
                adapter.loadItems(subjectList);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });




        //전공 과목들을 체크한 후 완료버튼을 눌러준다
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }
}