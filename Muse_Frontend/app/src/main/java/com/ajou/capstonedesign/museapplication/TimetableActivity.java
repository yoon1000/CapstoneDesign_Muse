package com.ajou.capstonedesign.museapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.Sticker;
import com.github.tlaabs.timetableview.HighlightMode;
import com.github.tlaabs.timetableview.SaveManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class TimetableActivity extends AppCompatActivity {

    private Toolbar toolbar;


    private Button maketimetable;

    private Spinner selectsemester;
    private Spinner selectoption;

    private TimetableView timetableView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MUSE");


        maketimetable = (Button)findViewById(R.id.maketimetable);



        timetableView = (TimetableView) findViewById(R.id.timetable);

        selectsemester = (Spinner)findViewById(R.id.selectsemester);
        selectoption = (Spinner)findViewById(R.id.selectoption);


        selectsemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(parent.getItemAtPosition(position).toString()){
                    case "1학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "1");break;
                    case "2학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "2");break;
                    case "3학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "3");break;
                    case "4학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "4");break;
                    case "5학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "5");break;
                    case "6학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "6");break;
                    case "7학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "7");break;
                    case "8학기":
                        SharedPreference.setAttribute(view.getContext(), "selectedsemester", "8");break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectoption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "월 공강":
                        SharedPreference.setAttribute(view.getContext(), "selectedoption", "월");break;
                    case "금 공강":
                        SharedPreference.setAttribute(view.getContext(), "selectedoption", "금");break;
                    case "점심 시간 확보":
                        SharedPreference.setAttribute(view.getContext(), "selectedoption", "/13/");break;
                    case "오후 수업 위주":
                        SharedPreference.setAttribute(view.getContext(), "selectedoption", "/1/");break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        maketimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TimetableActivity.this, TimetableOne.class);
                startActivity(intent);
                finish();

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
}
