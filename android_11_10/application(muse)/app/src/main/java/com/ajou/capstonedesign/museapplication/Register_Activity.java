package com.ajou.capstonedesign.museapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Register_Activity extends AppCompatActivity {

    private EditText et_password, et_passwordConfirm;
    ImageView setImage;
    Button btnschool;
    boolean passwordcheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        et_password = (EditText)findViewById(R.id.et_password);
        et_passwordConfirm = (EditText)findViewById((R.id.et_passwordConfirm));
        setImage = (ImageView)findViewById(R.id.setImage);
        btnschool = (Button)findViewById(R.id.selectschool);

        //비밀번호인와 비밀번호확인 일치하는지 체크
        et_passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_password.getText().toString().equals(et_passwordConfirm.getText().toString())){
                    setImage.setImageResource(R.drawable.check);//비밀번호 확인까지 됐으면 초록색 체크가 뜬다
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

        btnschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, ChooseShoolActivity.class);
                startActivity(intent);
            }
        });

    }

    /*public boolean datecheck (){
        boolean check = true;
        EditText editText1 = (EditText) findViewById(R.id.et_Number) ;
        String strText = editText1.getText().toString() ;
        int number = Integer.parseInt(strText);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        String year = yearFormat.format(currentTime);
        int thisyear = Integer.parseInt(year);

        if(number > thisyear)
            check = false;
        return check;
    }*/



    public void btnMajor(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("전공 선택");
        builder.setItems(R.array.Major_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                String[] items = getResources().getStringArray(R.array.Major_list);
                Toast.makeText(getApplicationContext(), items[pos], Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void btnRegister(View view){
        if (passwordcheck == true) {//회원가입 후 로그인 페이지로 넘어가
           /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("다음 정보를 확인하세요.")
                    .setMessage("id: ").setMessage(et_id.getText().toString())
                    .setMessage("name: ").setMessage(et_name.getText().toString())
                    .setMessage("num: ").setMessage(et_Number.getText().toString());
            builder.show();*/

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if(passwordcheck == false)  //비밀번호 확인이 되지 않으면 넘어가지 못함
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
    }


}
