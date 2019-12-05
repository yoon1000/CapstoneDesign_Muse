package com.ajou.capstonedesign.museapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;
//import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.Sticker;
import com.github.tlaabs.timetableview.HighlightMode;
import com.github.tlaabs.timetableview.SaveManager;


import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TimetableActivity extends AppCompatActivity {//implements View.OnClickListener
    //private Context context;
    //public static final int REQUEST_ADD = 1;
    //public static final int REQUEST_EDIT = 2;

    //private Button addBtn;
    //private Button clearBtn;
    //private Button saveBtn;
    //private Button loadBtn;

    private Toolbar toolbar;

    private Button timetablesemester;
    private Button timetableoption;
    private Button maketimetable;

    private TextView result;
    String selectedsemster = "";
    String selectedoption = "";

    private TimetableView timetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        timetablesemester = (Button)findViewById(R.id.timetablesemester);
        timetableoption = (Button) findViewById(R.id.timetableoption);
        maketimetable = (Button)findViewById(R.id.maketimetable);

        result = (TextView)findViewById(R.id.optionresult);

        timetable = (TimetableView) findViewById(R.id.timetable);

        //사용자가 원하는 학기 고르기
        timetablesemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[]{"1학기", "2학기", "3학기", "4학기", "5학기", "6학기","7학기", "8학기"};
                final int[] selectedindex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(TimetableActivity.this);
                dialog.setTitle("학기 고르기")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedindex[0] = which;

                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedsemster = items[selectedindex[0]];
                                SharedPreference.setAttribute(v.getContext(), "selectedsemster", items[selectedindex[0]]);
                                Toast.makeText(TimetableActivity.this, items[selectedindex[0]], Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

            }
        });

        //사용자가 원하는 옵션 고르기
        timetableoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[]{"월 공강", "금 공강", "점심시간 확보", "오후 수업 위주"};
                final int[] selectedindex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(TimetableActivity.this);
                dialog.setTitle("옵션 고르기")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedindex[0] = which;

                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedoption = items[selectedindex[0]];
                                SharedPreference.setAttribute(v.getContext(), "selectedoption", items[selectedindex[0]]);
                                Toast.makeText(TimetableActivity.this, items[selectedindex[0]], Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

            }
        });

        maketimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //예시: 월/25/29,수/25/29
                ArrayList<Schedule> schedules1 = new ArrayList<Schedule>();

                Schedule schedule1 = new Schedule();
                schedule1.setClassTitle("IT전문영어"); // sets subject
                //schedule1.setClassPlace("팔달409"); // sets place
                //schedule1.setProfessorName("김떙땡"); // sets professor
                schedule1.setStartTime(new Time(setHour(25), setMinute(25)));
                schedule1.setEndTime(new Time(setHour(29+1), setMinute(29+1)));
                schedule1.setDay(setDay("월"));
                //schedule1.setDay(setDay("수"));

                schedules1.add(schedule1);

                timetable.add(schedules1);
            }
        });


       /* ArrayList<Schedule> schedules1 = new ArrayList<Schedule>();
        ArrayList<Schedule> schedules2 = new ArrayList<Schedule>();
        ArrayList<Schedule> schedules3 = new ArrayList<Schedule>();
        ArrayList<Schedule> schedules4 = new ArrayList<Schedule>();
        ArrayList<Schedule> schedules5 = new ArrayList<Schedule>();

        Schedule schedule1 = new Schedule();
        schedule1.setClassTitle("예시 과목"); // sets subject
        schedule1.setClassPlace("팔달409"); // sets place
        schedule1.setProfessorName("김떙땡"); // sets professor
        schedule1.setStartTime(new Time(12,0)); // sets the beginning of class time (hour,minute)
        schedule1.setEndTime(new Time(13,15)); // sets the end of class time (hour,minute)
        schedule1.setDay(2);//2 = wednesday

        Schedule schedule2 = new Schedule();
        schedule2.setClassTitle("예시 과목2"); // sets subject
        schedule2.setClassPlace("팔달1025"); // sets place
        schedule2.setProfessorName("김모모"); // sets professor
        schedule2.setStartTime(new Time(10,30)); // sets the beginning of class time (hour,minute)
        schedule2.setEndTime(new Time(11,45)); // sets the end of class time (hour,minute)
        schedule2.setDay(1);//2 = wednesday

        Schedule schedule3 = new Schedule();
        schedule3.setClassTitle("예시 과목3"); // sets subject
        schedule3.setClassPlace("팔달309"); // sets place
        schedule3.setProfessorName("강떙땡"); // sets professor
        schedule3.setStartTime(new Time(16,30)); // sets the beginning of class time (hour,minute)
        schedule3.setEndTime(new Time(21,00)); // sets the end of class time (hour,minute)
        schedule3.setDay(0);//2 = wednesday



        schedules1.add(schedule1);
        schedules2.add(schedule2);
        schedules3.add(schedule3);



        //.. add one or more schedules
        timetable.add(schedules1);
        timetable.add(schedules2);
        timetable.add(schedules3);*/

        //init();
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
                finish();
                return true;

            case R.id.action_timetable:
                Toast.makeText(getApplicationContext(),"시간표 클릭", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, TimetableActivity.class);
                startActivity(intent2);
                finish();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

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

    public int setDay(String day){
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


}
