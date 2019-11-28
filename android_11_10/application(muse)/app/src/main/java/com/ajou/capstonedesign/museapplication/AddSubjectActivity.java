package com.ajou.capstonedesign.museapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;

import az.plainpie.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AddSubjectActivity extends AppCompatActivity {
    private Button majorbtn;
    private Button nonmajorbtn;

    private  Button addfinal;
    private Boolean checkmajor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        majorbtn = (Button) findViewById(R.id.majorbtn);
        nonmajorbtn = (Button)findViewById(R.id.nonmajorbtn);
        addfinal = (Button) findViewById(R.id.addfinal);

        //전공 선택을 눌렀을 때 전공과목을 띄워주는 액티비티로 넘어감
        majorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSubjectActivity.this, ChooseSubjectActivity.class);
                startActivity(intent);
            }
        });
        //교양 선택을 눌렀을 때 교양과목을 띄워주는 액티비티로 넘어감
        nonmajorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSubjectActivity.this, ChooseSubject2Activity.class);
                startActivity(intent);

            }
        });


        //내장메모리(resultmajor, resultnonmajor)에 있는 값을 통신을 통해 post해주면 studentIfo에 반영되어 파이차트에 학점이 늘어난다
        String majorresult = SharedPreference.getAttribute(AddSubjectActivity.this, "resultmajor");
        String majorresult2 = "[" + majorresult + "]";

        String nonmajorresult = SharedPreference.getAttribute(AddSubjectActivity.this, "resultnonmajor");
        String nonmajorresult2 = "[" + nonmajorresult +"]";


        //추가하기를 눌렀을 때 서버에 반영되어야
        addfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///id 값을 서버로 보내주는 거 있어야함
                JsonObject subjectData = new JsonObject();

                subjectData.addProperty("id",SharedPreference.getAttribute(AddSubjectActivity.this,"id"));
                subjectData.addProperty("subject",majorresult2);
                subjectData.addProperty("subject2",nonmajorresult2);


                RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
                Call<JsonObject> subjectdata = retrofitCommunication.majorsubject(subjectData);
                Call<JsonObject> subjectdata2 = retrofitCommunication.nonmajorsubject(subjectData);

                //전공과목이 서버로 들어가는 부분
                subjectdata.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body().get("code").getAsInt() == 200) {

                        } else {
                            Toast.makeText(AddSubjectActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AddSubjectActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });


                //교양과목이 서버로 들어가는 부분
                subjectdata2.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body().get("code").getAsInt() == 200) {

                        } else {
                            Toast.makeText(AddSubjectActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }



                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(AddSubjectActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });
                //서버에 반영되고 나면 파이차트 있는 페이지로 넘어가
                finish();
            }
        });
    }

}
