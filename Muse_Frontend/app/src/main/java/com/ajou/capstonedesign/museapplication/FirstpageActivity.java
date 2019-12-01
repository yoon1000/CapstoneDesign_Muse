package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import az.plainpie.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class FirstpageActivity extends AppCompatActivity {

    private Button timetable;
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

    private TextView toeicscore;
    private TextView textView3;
    private TextView textViewSemester;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
//        //뒤로가기버튼
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //뒤로가기버튼 아이콘설정
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setTitle("MUSE");

        timetable = (Button)findViewById(R.id.timetable);
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

        toeicscore = (TextView)findViewById(R.id.toeicscore);
        textView3 = (TextView) findViewById(R.id.textView3);
        textViewSemester = (TextView) findViewById(R.id.textViewSemester);

        pieView1.setPercentageBackgroundColor(getResources().getColor(R.color.main));
        pieView2.setPercentageBackgroundColor(getResources().getColor(R.color.main));



        //id 값을 서버로 보내주기
        JsonObject userID = new JsonObject();

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

        JsonObject userdata = new JsonObject();
        userdata.addProperty("major", SharedPreference.getAttribute(FirstpageActivity.this,"major"));
        userdata.addProperty("num", SharedPreference.getAttribute(FirstpageActivity.this,"num"));

        Call<JsonObject> nonsubject = retrofitCommunication.nonSubject(userdata);

        nonsubject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("code").getAsInt() == 200) {
                    textView3.setText(response.body().get("result").toString());
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

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstpageActivity.this, TimetableActivity.class);
                startActivity(intent);
            }
        });

        //자신의 학적정보를 추가하는 액티비티로 넘어감
        addsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstpageActivity.this, AddSubjectActivity.class);
                startActivity(intent);
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
                usersemester.addProperty("semester", "1학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            textViewSemester.setText(response.body().get("result").toString());
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
                usersemester.addProperty("semester", "2학기");

                Call<JsonObject> semester = retrofitCommunication.majorsemester(usersemester);

                semester.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {
                            textViewSemester.setText(response.body().get("result").toString());
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
                            textViewSemester.setText(response.body().get("result").toString());
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
                            textViewSemester.setText(response.body().get("result").toString());
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
                            textViewSemester.setText(response.body().get("result").toString());
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
                            textViewSemester.setText(response.body().get("result").toString());
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
                            textViewSemester.setText(response.body().get("result").toString());
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
                            textViewSemester.setText(response.body().get("result").toString());
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
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }

}
