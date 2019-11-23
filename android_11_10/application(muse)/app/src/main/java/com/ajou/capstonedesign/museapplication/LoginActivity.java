package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginActivity extends AppCompatActivity {
    String string;

    private Button loginbtn;
    private Button registerbtn;

    private EditText et_id;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        loginbtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {


                JsonObject logindata = new JsonObject();
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
