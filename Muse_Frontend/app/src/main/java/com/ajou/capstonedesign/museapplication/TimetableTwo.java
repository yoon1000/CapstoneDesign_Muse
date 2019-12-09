package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class TimetableTwo extends AppCompatActivity {
    private Toolbar toolbar;

    private Button back;
    private Button forward;

    String[] splitsubject ={};
    private TimetableView timetableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_two);

        back = (Button)findViewById(R.id.back);
        forward = (Button)findViewById(R.id.forward);

        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MUSE");

        timetableView = (TimetableView) findViewById(R.id.timetable);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableTwo.this, TimetableOne.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableTwo.this, TimetableThree.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        JsonObject userData = new JsonObject();

        userData.addProperty("id",SharedPreference.getAttribute(TimetableTwo.this,"id"));
        userData.addProperty("semester", parseInt(SharedPreference.getAttribute(TimetableTwo.this, "selectedsemester")));
        userData.addProperty("option", SharedPreference.getAttribute(TimetableTwo.this, "selectedoption"));

        RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
        Call<JsonObject> timetable = retrofitCommunication.timetable(userData);

        timetable.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("code").getAsInt() == 200) {
                    String rawdata1 = response.body().get("result2").toString();
                    Log.d("JsonObject1",rawdata1);

                    if(rawdata1.contains("&")){



                        AlertDialog.Builder alert = new AlertDialog.Builder(TimetableTwo.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(TimetableTwo.this, TimetableOne.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                finish();  //닫기
                            }
                        });
                        alert.setMessage("더이상 추천 시간표가 없습니다.");
                        alert.show();

                    }

                    else {

                        ArrayList<Schedule> schedules0 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules1 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules2 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules3 = new ArrayList<Schedule>();

                        ArrayList<Schedule> schedules4 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules5 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules6 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules7 = new ArrayList<Schedule>();

                        ArrayList<Schedule> schedules8 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules9 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules10 = new ArrayList<Schedule>();
                        ArrayList<Schedule> schedules11 = new ArrayList<Schedule>();

                        Schedule schedule0 = new Schedule();
                        Schedule schedule1 = new Schedule();
                        Schedule schedule2 = new Schedule();
                        Schedule schedule3 = new Schedule();

                        Schedule schedule4 = new Schedule();
                        Schedule schedule5 = new Schedule();
                        Schedule schedule6 = new Schedule();
                        Schedule schedule7 = new Schedule();

                        Schedule schedule8 = new Schedule();
                        Schedule schedule9 = new Schedule();
                        Schedule schedule10 = new Schedule();
                        Schedule schedule11 = new Schedule();

                        splitsubject = rawdata1.split("\\]");//]로 나누면 과목 별로 나뉜다

                        //서버에서 받아온 내용을 기반으로 과목과 시작시간,종료시간을 스케줄이 지정해준다
                        Getschedule(splitsubject[0], schedule0, schedule4, schedule8);
                        Getschedule(splitsubject[1], schedule1, schedule5, schedule9);
                        Getschedule(splitsubject[2], schedule2, schedule6, schedule10);
                        Getschedule(splitsubject[3], schedule3, schedule7, schedule11);

                        schedules0.add(schedule0);
                        schedules1.add(schedule1);
                        schedules2.add(schedule2);
                        schedules3.add(schedule3);
                        schedules4.add(schedule4);
                        schedules5.add(schedule5);
                        schedules6.add(schedule6);
                        schedules7.add(schedule7);
                        schedules8.add(schedule8);
                        schedules9.add(schedule9);
                        schedules10.add(schedule10);
                        schedules11.add(schedule11);

                        timetableView.add(schedules0);
                        timetableView.add(schedules1);
                        timetableView.add(schedules2);
                        timetableView.add(schedules3);
                        timetableView.add(schedules4);
                        timetableView.add(schedules5);
                        timetableView.add(schedules6);
                        timetableView.add(schedules7);
                        timetableView.add(schedules8);
                        timetableView.add(schedules9);
                        timetableView.add(schedules10);
                        timetableView.add(schedules11);
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void Getschedule(String income, Schedule schedule, Schedule schedule2, Schedule schedule3){
        String[] split = {};
        String[] subjecttime = {};
        String[] time1 = {};
        String[] time2 = {};
        String[] time3 = {};
        split = income.split("\"");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceAll("\\[", "");
        }

        //과목명 정해주기
        schedule.setClassTitle(split[1]);
        schedule2.setClassTitle(split[1]);
        schedule3.setClassTitle(split[1]);

        subjecttime = split[3].split(",");
        time1 = subjecttime[0].split("/");
        //[0]은 요일, [1]은 시작시간, [2]은 종료시간

        schedule.setDay(setdays(time1[0]));
        schedule.setStartTime(new com.github.tlaabs.timetableview.Time(setHour(parseInt(time1[1])), setMinute(parseInt(time1[1]))));
        schedule.setEndTime(new com.github.tlaabs.timetableview.Time(setHour(parseInt(time1[2])+1), setMinute(parseInt(time1[2])+1)));

        int length = subjecttime.length;
        if(length >=2) {
            time2 = subjecttime[1].split("/");
            schedule2.setDay(setdays(time2[0]));
            schedule2.setStartTime(new com.github.tlaabs.timetableview.Time(setHour(parseInt(time2[1])), setMinute(parseInt(time2[1]))));
            schedule2.setEndTime(new Time(setHour(parseInt(time2[2]) + 1), setMinute(parseInt(time2[2]) + 1)));
        }

        if(length ==3){
            time3 = subjecttime[2].split("/");
            schedule3.setDay(setdays(time3[0]));
            schedule3.setStartTime(new com.github.tlaabs.timetableview.Time(setHour(parseInt(time3[1])), setMinute(parseInt(time3[1]))));
            schedule3.setEndTime(new com.github.tlaabs.timetableview.Time(setHour(parseInt(time3[2])+1), setMinute(parseInt(time3[2])+1)));

        }
    }



    //서버로부터 가져온 시간을 실제 시간으로 바꿔주기
    public int setHour(int time) {
        int H;
        if(time%4==0)
            H = time/4 +8;
        else
            H = time/4 +9;
        return H;
    }

    public int setMinute(int time) {
        int m=0;
        switch(time%4){
            case 1: m = 0;
                break;
            case 2: m = 15;
                break;
            case 3: m = 30;
                break;
            case 0: m = 45;
                break;
        }
        return m;
    }

    public int setdays(String day){
        int d = 0;
        switch (day){
            case "월": d = 0;
                break;
            case "화": d = 1;
                break;
            case "수": d = 2;
                break;
            case "목": d = 3;
                break;
            case "금": d = 4;
                break;
        }
        return d;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TimetableTwo.this, TimetableActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
