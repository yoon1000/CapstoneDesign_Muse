package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import az.plainpie.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class FirstpageActivity extends AppCompatActivity {

    private Button addsubject;
    private PieView pieView1;
    private PieView pieView2;

    private Button firstgrd;
    private Button secondgrd;
    private Button thirdgrd;
    private Button fourthgrd;

    private TextView toeicscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        addsubject = (Button)findViewById(R.id.addsubject);
        pieView1 = (PieView) findViewById(R.id.pieViewMajor);
        pieView2 = (PieView) findViewById(R.id.pieViewNonMajor);

        firstgrd = (Button)findViewById(R.id.firstgrd);
        secondgrd = (Button)findViewById(R.id.secondgrd);
        thirdgrd = (Button)findViewById(R.id.thirdgrd);
        fourthgrd = (Button)findViewById(R.id.fourthgrd);

        toeicscore = (TextView)findViewById(R.id.toeicscore);

        pieView1.setPercentageBackgroundColor(getResources().getColor(R.color.main));
        pieView2.setPercentageBackgroundColor(getResources().getColor(R.color.main));

        ////id 값을 서버로 보내주는 거 있어야함
        JsonObject userID = new JsonObject();

        //userID.addProperty(SharedPreference.getAttribute(FristpageActivity.this,"id"));
        userID.addProperty("id",SharedPreference.getAttribute(FirstpageActivity.this,"id"));

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
                    Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                            .show();
                }

            }



            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
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
                    Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                            .show();
                }

            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                        .show();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });


        toeicscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                CustomDialog customDialog = new CustomDialog(FirstpageActivity.this);

                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                customDialog.callFunction(toeicscore);
            }
        });

        addsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstpageActivity.this, AddSubjectActivity.class);
                startActivity(intent);
            }
        });

        firstgrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstgrd.setBackgroundColor(getResources().getColor(R.color.main));
                secondgrd.setBackgroundColor(getResources().getColor(R.color.back));
                thirdgrd.setBackgroundColor(getResources().getColor(R.color.back));
                fourthgrd.setBackgroundColor(getResources().getColor(R.color.back));
            }
        });

        secondgrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstgrd.setBackgroundColor(getResources().getColor(R.color.back));
                secondgrd.setBackgroundColor(getResources().getColor(R.color.main));
                thirdgrd.setBackgroundColor(getResources().getColor(R.color.back));
                fourthgrd.setBackgroundColor(getResources().getColor(R.color.back));
            }
        });

        thirdgrd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firstgrd.setBackgroundColor(getResources().getColor(R.color.back));
                secondgrd.setBackgroundColor(getResources().getColor(R.color.back));
                thirdgrd.setBackgroundColor(getResources().getColor(R.color.main));
                fourthgrd.setBackgroundColor(getResources().getColor(R.color.back));
            }
        });

        fourthgrd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firstgrd.setBackgroundColor(getResources().getColor(R.color.back));
                secondgrd.setBackgroundColor(getResources().getColor(R.color.back));
                thirdgrd.setBackgroundColor(getResources().getColor(R.color.back));
                fourthgrd.setBackgroundColor(getResources().getColor(R.color.main));
            }
        });


    }
}
