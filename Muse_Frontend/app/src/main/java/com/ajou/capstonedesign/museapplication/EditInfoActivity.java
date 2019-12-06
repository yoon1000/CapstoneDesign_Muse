package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EditInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        edit = (TextView)findViewById(R.id.editSubject);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfoActivity.this, EditSubjectActivity.class);
                startActivityForResult(intent, 103);
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
                finish();
                return true;
            case R.id.action_timetable:
                Toast.makeText(getApplicationContext(),"시간표 클릭", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, TimetableActivity.class);
                startActivity(intent2);
                finish();
            case R.id.action_edit:
                Toast.makeText(getApplicationContext(),"수정하기 클릭", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(this, EditInfoActivity.class);
                startActivity(intent3);
                finish();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }
}
