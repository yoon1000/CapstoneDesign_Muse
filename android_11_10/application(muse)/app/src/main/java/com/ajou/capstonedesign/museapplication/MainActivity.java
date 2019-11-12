package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "홈"))
                .addItem(new BottomNavigationItem(R.drawable.calender, "시간"))
                .addItem(new BottomNavigationItem(R.drawable.setting, "설정"))
                //.addItem(new BottomNavigationItem(R.drawable.check, "Movies & TV"))
                //.addItem(new BottomNavigationItem(R.drawable.check, "Games"))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }
}
