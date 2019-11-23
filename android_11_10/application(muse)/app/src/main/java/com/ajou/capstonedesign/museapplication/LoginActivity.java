package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.AccessControlContext;


public class LoginActivity extends AppCompatActivity {
    private Button loginbtn;
    private Button registerbtn;

    private EditText et_id;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreference.removeAttribute(LoginActivity.this, "result");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = (Button)findViewById(R.id.loginbutton);
        registerbtn = (Button)findViewById(R.id.registerbutton);

        et_id = (EditText)findViewById(R.id.et_id);
        et_password = (EditText)findViewById(R.id.et_password);

        //회원가입 화면으로 넘어가기
        registerbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register_Activity.class);
                startActivity(intent);
            }
        });

        //로그인 버튼 누르기
        loginbtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {


                JsonObject logindata = new JsonObject();
                //사용자가 쓴 edittext들을 넣어준다
                logindata.addProperty("id", et_id.getText().toString());
                logindata.addProperty("password", et_password.getText().toString());

                RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
                Call<JsonObject> login = retrofitCommunication.userLogin(logindata);

                login.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().get("code").getAsInt() == 200) {

                            Log.d("Get", response.body().toString());

                            JsonArray jsonArray = response.body().getAsJsonArray("result");
                            LoginData.getInstance().setID(jsonArray.get(0).getAsJsonObject().get("id").getAsString());
                            LoginData.getInstance().setPW(jsonArray.get(0).getAsJsonObject().get("password").getAsString());
                            StudentInfo.getInstance().setStudentmajor(jsonArray.get(0).getAsJsonObject().get("major").getAsString());

                            //내장메모리를 이용해 id값과 major값을 지정해준다
                            SharedPreference.setAttribute(v.getContext(), "id", jsonArray.get(0).getAsJsonObject().get("id").getAsString());
                            SharedPreference.setAttribute(v.getContext(), "major", jsonArray.get(0).getAsJsonObject().get("major").getAsString());

                            //로그인이 되면 MainActivity로 넘어가준다
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, response.body().get("code").getAsString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                .show();
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });
    }

}
