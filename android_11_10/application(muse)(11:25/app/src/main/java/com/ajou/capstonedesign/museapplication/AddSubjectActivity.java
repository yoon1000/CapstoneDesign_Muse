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
    private TextView getcheck;
    private TextView getcheck2;
    private Boolean checkmajor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        majorbtn = (Button) findViewById(R.id.majorbtn);
        nonmajorbtn = (Button)findViewById(R.id.nonmajorbtn);
        addfinal = (Button) findViewById(R.id.addfinal);
       // getcheck = (TextView)findViewById(R.id.getcheck);
       // getcheck2 = (TextView)findViewById(R.id.getcheck2);

        ////전공 선택을 눌렀을 때
        majorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSubjectActivity.this, ChooseSubjectActivity.class);
                startActivity(intent);
            }
        });
        ////교양 선택을 눌렀을 때
        nonmajorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSubjectActivity.this, ChooseSubject2Activity.class);
                startActivity(intent);

            }
        });


        //내장메모리에 있는 값을 텍스트뷰에 띄워준다
        String idid = SharedPreference.getAttribute(AddSubjectActivity.this, "id");
        String majorresult = SharedPreference.getAttribute(AddSubjectActivity.this, "resultmajor");
        String majorresult2 = "[" + majorresult + "]";
//        getcheck.setText(majorresult2);
//        getcheck2.setText(idid);

        //추가하기를 눌렀을 때 서버에 반영되어야
        addfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///id 값을 서버로 보내주는 거 있어야함
                JsonObject subjectData = new JsonObject();

                //userID.addProperty(SharedPreference.getAttribute(FristpageActivity.this,"id"));
                subjectData.addProperty("id",SharedPreference.getAttribute(AddSubjectActivity.this,"id"));
                subjectData.addProperty("subject",majorresult2);


                RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
                Call<JsonObject> subjectdata = retrofitCommunication.majorsubject(subjectData);

                subjectdata.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body().get("code").getAsInt() == 200) {
                            Intent intent = new Intent(AddSubjectActivity.this, FristpageActivity.class);
                            startActivity(intent);
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

            }
        });


        /*String nonmajorresult = SharedPreference.getAttribute(AddSubjectActivity.this, "resultnonmajor");
        String nonmajorresult2 = "[" + nonmajorresult + "]";
        getcheck2.setText(nonmajorresult2);*/
    }
    /*public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentManager.findFragmentById(R.id.frameLayout);
        fragmentTransaction.replace(R.id.frameLayout, fragment);//framelayout 지정해줘야함
        fragmentTransaction.commit();
    }*/
}
