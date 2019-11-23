package com.ajou.capstonedesign.museapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddSubjectActivity extends AppCompatActivity {
    private Button majorbtn;
    private Button nonmajorbtn;
    private TextView getcheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        majorbtn = (Button) findViewById(R.id.majorbtn);
        nonmajorbtn = (Button)findViewById(R.id.nonmajorbtn);
        getcheck = (TextView)findViewById(R.id.getcheck);

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

            }
        });


        String result2 = SharedPreference.getAttribute(AddSubjectActivity.this, "result");
        String result3 = "[" + result2 + "]";
        getcheck.setText(result3);
    }
    /*public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentManager.findFragmentById(R.id.frameLayout);
        fragmentTransaction.replace(R.id.frameLayout, fragment);//framelayout 지정해줘야함
        fragmentTransaction.commit();
    }*/
}
