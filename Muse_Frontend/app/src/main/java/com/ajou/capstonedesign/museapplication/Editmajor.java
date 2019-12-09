package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Editmajor extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditSubjectAdapter recyclerAdapter;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmajor);

        recyclerView = (RecyclerView) findViewById(R.id.majorsubjectlist);
        btn = (Button) findViewById(R.id.completebtn);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);

        JsonObject logindata = new JsonObject();
        logindata.addProperty("id",SharedPreference.getAttribute(Editmajor.this, "id"));

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> majorlist = retrofitCommunication.completedmajorsubject(logindata);
        majorlist.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                JsonObject res = response.body();

                Log.d("Received", res.toString());

                List<SubjectList> subjectList = gson.fromJson(res.get("result"), new TypeToken<List<SubjectList>>(){}.getType());

                recyclerAdapter = new EditSubjectAdapter(subjectList);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                String Resultmajor = "";
                List<SubjectList> resultmajor = ((EditSubjectAdapter)recyclerAdapter).getSubject();
                for(int i = 0;i<resultmajor.size();i++){
                    SubjectList singlemajor = resultmajor.get(i);
                    if (singlemajor.isSelected() == true) {

                        data = data + "," + singlemajor.getSubject().toString();
                        Resultmajor = "["+ data.replaceFirst(",", "")+"]";

                    }
                }

                Intent intent = new Intent(Editmajor.this, EditInfoActivity.class);
                intent.putExtra("deletemajor", Resultmajor);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
        super.onBackPressed();
    }

}
