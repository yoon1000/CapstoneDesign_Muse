/*
package com.ajou.capstonedesign.museapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import az.plainpie.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class FristpageActivity extends AppCompatActivity {

    private CardView card_view;
    private CardView card_view2;
    private Button addsubject;
    private PieView pieView1;
    private PieView pieView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fristpage);


        //card_view = (CardView) findViewById(R.id.card_view);
        //card_view2 = (CardView) findViewById(R.id.card_view2);
        addsubject = (Button)findViewById(R.id.addsubject);
        pieView1 = (PieView) findViewById(R.id.pieViewMajor);
        pieView2 = (PieView) findViewById(R.id.pieViewNonMajor);

        pieView1.setPercentageBackgroundColor(getResources().getColor(R.color.main));
        pieView2.setPercentageBackgroundColor(getResources().getColor(R.color.main));



        ////id 값을 서버로 보내주는 거 있어야함
        JsonObject userID = new JsonObject();

        //userID.addProperty(SharedPreference.getAttribute(FristpageActivity.this,"id"));
        userID.addProperty("id",SharedPreference.getAttribute(FristpageActivity.this,"id"));

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> majorCredit = retrofitCommunication.majorcreditSum(userID);

        majorCredit.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("code").getAsInt() == 200) {
                    String creditdata1 = response.body().get("result").toString();
                    String[] splitCredit1 = creditdata1.split(":");
                    String creditsumdata1 = splitCredit1[1];
                    String[] splitCreditSum1 = creditsumdata1.split("[}]");
                    Float persent_creditSum1 = parseFloat(splitCreditSum1[0]);
                    Integer creditSum1 = parseInt(splitCreditSum1[0]);
                    pieView1.setPercentage(persent_creditSum1/75*100);
                    pieView1.setInnerText(creditSum1+"\n-\n75");
                } else {
                    Toast.makeText(FristpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                            .show();
                }

            }



            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FristpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                        .show();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });


        Call<JsonObject> nonmajorCredit = retrofitCommunication.nonmajorcreditSum(userID);

        nonmajorCredit.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("code").getAsInt() == 200) {
                    String creditdata2 = response.body().get("result").toString();
                    String[] splitCredit2 = creditdata2.split(":");
                    String creditsumdata2 = splitCredit2[1];
                    String[] splitCreditSum2 = creditsumdata2.split("[}]");
                    Float persent_creditSum2 = parseFloat(splitCreditSum2[0]);
                    Integer creditSum2 = parseInt(splitCreditSum2[0]);
                    PieView pieView2 = (PieView) findViewById(R.id.pieViewNonMajor);
                    pieView2.setPercentage(persent_creditSum2/65*100);
                    pieView2.setInnerText(creditSum2+"\n-\n65");
                } else {
                    Toast.makeText(FristpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                            .show();
                }

            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FristpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                        .show();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });

        */
/*card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FristpageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FristpageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*//*


        addsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FristpageActivity.this, AddSubjectActivity.class);
                startActivity(intent);
            }
        });


    }
}
*/
