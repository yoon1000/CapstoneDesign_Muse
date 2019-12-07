package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    private EditText idtext;
    private EditText passwordtext, passwordConfirm;
    private EditText nametext;
    private EditText numtext;

    private TextView majorselected;


    private ImageView setImage;
    private Button btnschool;
    private Button btnmajor;
    private Button btnRegister;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ChooseMajorAdapter recyclerAdapter;

    boolean passwordcheck = true;

    //전공 선택에서 선택한 것을 버튼 밑에 띄워준다
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedData = intent.getStringExtra("major");
            majorselected.setText("전공:" + receivedData);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

//        final ArrayList<String> arrayList = null;
//        ArrayAdapter<String> arrayAdapter;

        idtext = (EditText)findViewById(R.id.et_id);
        passwordtext = (EditText)findViewById(R.id.et_password);
        passwordConfirm = (EditText)findViewById((R.id.et_passwordConfirm));
        nametext = (EditText)findViewById(R.id.et_name);
        numtext = (EditText)findViewById(R.id.et_number);

        //리사이클러뷰에서 선택하면 바껴야 함
        majorselected = (TextView)findViewById(R.id.selected);

        setImage = (ImageView)findViewById(R.id.setImage);

        btnschool = (Button)findViewById(R.id.selectschool);
        btnmajor = (Button)findViewById(R.id.selectmajor);
        btnRegister = (Button)findViewById(R.id.Register);

        //전공 선택에서 선택한 것 가져오기
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("majorToregister"));

        //비밀번호 일치 확인,비밀번호 확인까지 됐으면 초록색 체크가 뜬다
        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordtext.getText().toString().equals(passwordConfirm.getText().toString())){
                    setImage.setImageResource(R.drawable.check);
                    passwordcheck = true;
                }
                else{
                    setImage.setImageResource(R.drawable.redmark);
                    passwordcheck = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //학교 선택: 선택한 항목 띄우기. 현재는 list에 넣어놓은 학교 목록이 뜸
        btnschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register_Activity.this);
                builder.setTitle("학교 선택");
                builder.setItems(R.array.School_List, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        String[] items = getResources().getStringArray(R.array.School_List);
                        Toast.makeText(getApplicationContext(),items[pos],Toast.LENGTH_LONG).show();//선택한 항목 띄우기->실제로는 데이터베이스에 반영
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //전공 선택:api를 통해 받아온 전공들을 새로운 액티비티에서 리사이클러뷰로 보여준다
        btnmajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, ChooseMajorActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

        });

        //회원가입 버튼 누르기,id/password/name/num/major가 retrofit을 통해 보내진다
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //회원 정보가 들어갈 곳에 안 쓴 부분이 있다면 넘어가지 않게 하려 했으나 아직은 안됨->나중에 수정
                if (idtext.getText() == null || passwordtext.getText() == null || nametext.getText() == null || numtext.getText() == null || majorselected.getText() == "전공:") {
                    Toast.makeText(getApplicationContext(), "빈 곳을 채워주십시오.", Toast.LENGTH_LONG).show();
                }
                else {



                    JsonObject registerdata = new JsonObject();
                    registerdata.addProperty("id", idtext.getText().toString());
                    registerdata.addProperty("password", passwordtext.getText().toString());
                    registerdata.addProperty("name", nametext.getText().toString());
                    registerdata.addProperty("num", numtext.getText().toString());
                    registerdata.addProperty("major", majorselected.getText().toString());

                    //default 학교:아주대학교
                    registerdata.addProperty("school", "아주대학교");

                    RetrofitCommunication retrofitCommunication = new RetrofitConnection().init();
                    Call<JsonObject> register = retrofitCommunication.userRegister(registerdata);

                    register.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().get("code").getAsInt() == 200) {
                                Toast.makeText(Register_Activity.this, "회원가입 성공", Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            } else {
                                Toast.makeText(Register_Activity.this, "회원가입 실패, 아이디 중복", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(Register_Activity.this, "정보받아오기 실패", Toast.LENGTH_LONG)
                                    .show();
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });


                    //비밀번호 확인이 되지 않으면 넘어가지 못함
                    if (passwordcheck == true && passwordConfirm != null)
                        finish();
                    else if (passwordcheck == false)
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}
