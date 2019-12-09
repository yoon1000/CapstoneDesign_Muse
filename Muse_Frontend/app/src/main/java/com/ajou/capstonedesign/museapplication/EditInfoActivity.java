package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
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

import com.google.gson.JsonObject;

public class EditInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Button editmajor;
    private Button editnonmajor;
    private Button editfinal;

    private String resultMajor;
    private String resultNonmajor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MUSE");

        editmajor = (Button) findViewById(R.id.editmajor);
        editnonmajor = (Button) findViewById(R.id.editnonmajor);
        editfinal = (Button) findViewById(R.id.editfinal);

        //전공 과목 중 수정할 목록으로 이동
        editmajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfoActivity.this, Editmajor.class);
                startActivityForResult(intent, 103);
            }
        });

        //교양 과목 중 수정할 목록으로 이동
        editnonmajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfoActivity.this, Editnonmajor.class);
                startActivityForResult(intent, 104);
            }
        });

        //추가하기를 눌렀을 때 서버에 반영되어야
        editfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject subjectData = new JsonObject();

                subjectData.addProperty("id", SharedPreference.getAttribute(EditInfoActivity.this, "id"));
                subjectData.addProperty("deleteMajorlist", resultMajor);

                RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
                Call<JsonObject> subjectdata = retrofitCommunication.deletemajor(subjectData);

                if (resultMajor != null) {
                    //전공과목이 서버로 들어가는 부분
                    subjectdata.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().get("code").getAsInt() == 200) {

                            } else {
                                Toast.makeText(EditInfoActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(EditInfoActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                    .show();
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });
                }

                JsonObject subjectData2 = new JsonObject();

                subjectData2.addProperty("id", SharedPreference.getAttribute(EditInfoActivity.this, "id"));
                subjectData2.addProperty("deleteNonmajorlist", resultNonmajor);


                Call<JsonObject> subjectdata2 = retrofitCommunication.deletenonmajor(subjectData2);

                if (resultNonmajor != null) {
                    //교양과목이 서버로 들어가는 부분
                    subjectdata2.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().get("code").getAsInt() == 200) {

                            } else {
                                Toast.makeText(EditInfoActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                        .show();
                            }

                        }


                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(EditInfoActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                    .show();
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });

                }

                //Toast.makeText(EditInfoActivity.this, "홈화면 가서 확인", Toast.LENGTH_SHORT);
                Intent intent = new Intent(EditInfoActivity.this, FirstpageActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 103){

            if(resultCode == Activity.RESULT_OK){
                String resultmajor = data.getStringExtra("deletemajor");
                resultMajor = resultmajor;
                Log.d("deletedata1", resultMajor);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }

        }
        if(requestCode == 104){
            if(resultCode == Activity.RESULT_OK){
                String resultnonmajor = data.getStringExtra("deletenonmajor");
                resultNonmajor = resultnonmajor;
                Log.d("deletedata2", resultNonmajor);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

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
