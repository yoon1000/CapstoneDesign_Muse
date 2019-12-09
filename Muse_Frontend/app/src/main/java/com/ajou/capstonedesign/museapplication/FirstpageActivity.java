package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import az.plainpie.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class FirstpageActivity extends AppCompatActivity {

    public static Context CONTEXT;

    private Button addsubject;
    private PieView pieView1;
    private PieView pieView2;

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;

    private Button firstOne;
    private Button firstTwo;
    private Button secondOne;
    private Button secondTwo;
    private Button thirdOne;
    private Button thirdTwo;
    private Button fourthOne;
    private Button fourthTwo;

    private TextView userscore;
    private TextView toeicscore;
    private TextView textView3;
    private TextView textViewSemester;
    private Toolbar toolbar;

    private ImageView passorfail;

    String recomend = "";
    String temp="";
    String[] splitsubject ={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        CONTEXT = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
//        //뒤로가기버튼
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //뒤로가기버튼 아이콘설정
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setTitle("MUSE");

        addsubject = (Button)findViewById(R.id.addsubject);
        pieView1 = (PieView) findViewById(R.id.pieViewMajor);
        pieView2 = (PieView) findViewById(R.id.pieViewNonMajor);


        firstOne = (Button)findViewById(R.id.firstOne);
        firstTwo = (Button)findViewById(R.id.firstTwo);
        secondOne = (Button)findViewById(R.id.secondOne);
        secondTwo = (Button)findViewById(R.id.secondTwo);
        thirdOne = (Button)findViewById(R.id.thirdOne);
        thirdTwo = (Button)findViewById(R.id.thirdTwo);
        fourthOne = (Button)findViewById(R.id.fourthOne);
        fourthTwo = (Button)findViewById(R.id.fourthTwo);

        userscore = (TextView)findViewById(R.id.userscore);
        toeicscore = (TextView)findViewById(R.id.toeicscore);
        textView3 = (TextView) findViewById(R.id.textView3);
        textViewSemester = (TextView) findViewById(R.id.textViewSemester);

        pieView1.setPercentageBackgroundColor(getResources().getColor(R.color.main));
        pieView2.setPercentageBackgroundColor(getResources().getColor(R.color.main));

        passorfail = (ImageView)findViewById(R.id.passorfail);

        JsonObject userData = new JsonObject();
        userData.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
        userData.addProperty("num", SharedPreference.getAttribute(FirstpageActivity.this,"num"));

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> requiredmajorcredit = retrofitCommunication.requiredmajorcredit(userData);

        requiredmajorcredit.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("code").getAsInt() == 200){
                    String rawdata = response.body().get("result").toString();
                    Log.d("졸업요건 전공학점:", rawdata);
                    String[] splitrequired = rawdata.split(":");
                    String split = splitrequired[1].replaceAll("[}]","");
                    String result = split.replaceAll("\\]","");
                    SharedPreference.setAttribute(FirstpageActivity.this, "requiredmajor", result);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        Call<JsonObject> requirednonmajorcredit = retrofitCommunication.requirednonmajorcredit(userData);

        requirednonmajorcredit.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("code").getAsInt() == 200){
                    String rawdata = response.body().get("result").toString();
                    Log.d("졸업요건 교양학점:", rawdata);
                    String[] splitrequired = rawdata.split(":");
                    String split = splitrequired[1].replaceAll("[}]","");
                    String result = split.replaceAll("\\]","");
                    SharedPreference.setAttribute(FirstpageActivity.this, "requirednonmajor", result);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });






        //id 값을 서버로 보내주기
        JsonObject userID = new JsonObject();
        userID.addProperty("id",SharedPreference.getAttribute(FirstpageActivity.this,"id"));


        Call<JsonObject> majorCredit = retrofitCommunication.majorcreditSum(userID);

        majorCredit.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("code").getAsInt() == 200) {
                    String creditdata1 = response.body().get("result").toString();
                    String[] splitCredit1 = creditdata1.split(":");
                    String creditsumdata1 = splitCredit1[1];
                    String[] splitCreditSum1 = creditsumdata1.split("[}]");
                    Float required = parseFloat(SharedPreference.getAttribute(FirstpageActivity.this, "requiredmajor"));
                    Log.d("requirddfadfasf", required.toString());

                    Float creditSum1 = parseFloat(splitCreditSum1[0]);
                    int requiredInt = parseInt(SharedPreference.getAttribute(FirstpageActivity.this, "requiredmajor"));
                    int creditSum1Int =  parseInt(splitCreditSum1[0]);;

                    Float persent_creditSum1 = creditSum1/required;
                    pieView1.setPercentage(persent_creditSum1*100);
                    pieView1.setInnerText(creditSum1Int+"\n-\n"+requiredInt);
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
                    Float required2 = parseFloat(SharedPreference.getAttribute(FirstpageActivity.this, "requirednonmajor"));
                    Float creditSum2 = parseFloat(splitCreditSum2[0]);
                    int required2Int = parseInt(SharedPreference.getAttribute(FirstpageActivity.this, "requirednonmajor"));
                    int creditSum2Int =  parseInt(splitCreditSum2[0]);;

                    Float persent_creditSum2 = creditSum2/required2;
                    pieView2.setPercentage(persent_creditSum2*100);
                    pieView2.setInnerText(creditSum2Int+"\n-\n"+required2Int);





                    //PieView pieView2 = (PieView) findViewById(R.id.pieViewNonMajor);
                    //pieView2.setPercentage(persent_creditSum2/65*100);
                    //pieView2.setInnerText(creditSum2+"\n-\n65");
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

        JsonObject userdata = new JsonObject();
        userdata.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
        userdata.addProperty("num", SharedPreference.getAttribute(FirstpageActivity.this,"num"));

        Call<JsonObject> nonsubject = retrofitCommunication.nonSubject(userdata);

        nonsubject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("code").getAsInt() == 200) {
                    String data = response.body().get("result").toString();//data=[{"language_grade":760}]
                    /*JsonArray data = response.body().get("result").getAsJsonArray();
                    String[] data = response.body().get("result").getAsJsonArray();//data=[{"language_grade":760}]*/

                    String[] split = data.split(":");
                    String nonsubjectdata = split[1];
                    String[] split2 = nonsubjectdata.split("[}]");
                    String nonsubject = split2[0];
                    //Integer nonsubjectint = parseInt(split2[0]);
                    textView3.setText("TOEIC 점수는 "+nonsubject+" 이상 받아야 합니다.");
                    SharedPreference.setAttribute(FirstpageActivity.CONTEXT, "nonsubject", nonsubject);
                }
                else {
                    Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                            .show();
                }

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                        .show();
                Log.e("TAG", "onFailure: " + t.getMessage() );
            }
        });

        //사용자의 토익점수 받아와서 비어있으면 다이얼로그 입력하는 창 띄워주고 받아오면 다이얼로그 입력하는 창 비우기
        JsonObject userID2 = new JsonObject();
        userID2.addProperty("id",SharedPreference.getAttribute(FirstpageActivity.this,"id"));

        Call<JsonObject> userScore = retrofitCommunication.userscore(userID2);

        userScore.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String data = response.body().get("result").toString();
                String[] split = data.split(":");
                String userdata = split[1];
                String[] split2 = userdata.split("[}]");
                String userdata2 = split2[0];
                userscore.setText(userdata2);
                SharedPreference.setAttribute(FirstpageActivity.CONTEXT, "userscore", userdata2);

                int Nonsubject = parseInt(SharedPreference.getAttribute(FirstpageActivity.CONTEXT, "nonsubject"));
                int Userscore = parseInt(SharedPreference.getAttribute(FirstpageActivity.CONTEXT, "userscore"));
                if(Userscore>=Nonsubject){
                    passorfail.setImageResource(R.drawable.pass);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



        //사용자가 자신의 토익 점수를 입력하는 다이얼로그를 호출한다
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





        //자신의 학적정보를 추가하는 액티비티로 넘어감
        addsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstpageActivity.this, AddSubjectActivity.class);
                startActivity(intent);
                finish();
            }
        });



        //학기별로 나타내주는 부분
        firstOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.main));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", 1);

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            Log.d("1번과목", recomend);
                            for(int i=1;i<splitsubject.length-1;i++){
                                temp += content2(splitsubject[i])+ "\n";
                            }
                            Log.d("나머지과목", temp);
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        firstTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.main));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", 2);

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();
                            Log.d("data", data);

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1; i<splitsubject.length-1; i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        secondOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.main));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", "3학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1;i<splitsubject.length-1;i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        secondTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.main));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", "4학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1;i<splitsubject.length-1;i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        thirdOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.main));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", "5학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1; i<splitsubject.length-1; i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        thirdTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.main));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", "6학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1;i<splitsubject.length-1;i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        fourthOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.main));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.back));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", "7학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1;i<splitsubject.length-1;i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

        fourthTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOne.setBackgroundColor(getResources().getColor(R.color.back));
                firstTwo.setBackgroundColor(getResources().getColor(R.color.back));
                secondOne.setBackgroundColor(getResources().getColor(R.color.back));
                secondTwo.setBackgroundColor(getResources().getColor(R.color.back));
                thirdOne.setBackgroundColor(getResources().getColor(R.color.back));
                thirdTwo.setBackgroundColor(getResources().getColor(R.color.back));
                fourthOne.setBackgroundColor(getResources().getColor(R.color.back));
                fourthTwo.setBackgroundColor(getResources().getColor(R.color.main));

                JsonObject usersemester = new JsonObject();
                usersemester.addProperty("id", SharedPreference.getAttribute(FirstpageActivity.this,"id"));
                usersemester.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
                usersemester.addProperty("semester", "8학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            String data = response.body().get("result").toString();

                            splitsubject = data.split("[}]");
                            recomend = content(splitsubject[0]) + "\n";
                            for(int i=1;i<splitsubject.length-1;i++){
                                recomend = recomend + content2(splitsubject[i])+ "\n";
                            }
                            textViewSemester.setText(recomend);
                        }
                        else {
                            Toast.makeText(FirstpageActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(FirstpageActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_home:
                Toast.makeText(getApplicationContext(),"홈메뉴 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, FirstpageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                return true;

            case R.id.action_timetable:
                Toast.makeText(getApplicationContext(),"시간표 클릭", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, TimetableActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            case R.id.action_edit:
                Toast.makeText(getApplicationContext(),"수정하기 클릭", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(this, EditInfoActivity.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }

    //학기별 과목 받아올 때 파싱하는 함수(과목명들만 남김)
    public String content(String income){
        income = income.replaceAll("\\[", "");
        income = income.replaceAll("\\]", "");
        income = income.replaceAll("[{]","");
        income = income.replaceAll("\"", "");
        income = income.replaceAll("[:]","");
        income = income.replaceAll("subject_name","");
        income = income.replaceAll("required","");
        income = income.replaceAll("credit","");
        income = income.replaceAll(" ", "");

        String[] split ={};
        split = income.split(",");

        String outcome = split[0]+"("+split[1]+"/"+split[2]+")";
        Log.d("outcome1",outcome.toString());
        return outcome;
    }

    public String content2(String income){
        income = income.replaceAll("\\[", "");
        income = income.replaceAll("\\]", "");
        income = income.replaceAll("[{]","");
        income = income.replaceAll("\"", "");
        income = income.replaceAll("[:]","");
        income = income.replaceAll("subject_name","");
        income = income.replaceAll("required","");
        income = income.replaceAll("credit","");
        income = income.replaceAll(" ", "");

        String[] split ={};
        split = income.split(",");

        String outcome = split[1]+"("+split[2]+"/"+split[3]+")";
        Log.d("outcome2", outcome);
        return outcome;
    }

}
