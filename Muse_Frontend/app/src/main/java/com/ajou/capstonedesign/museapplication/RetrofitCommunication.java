package com.ajou.capstonedesign.museapplication;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitCommunication {
    //회원가입
    @POST("/users/register")
    Call<JsonObject> userRegister(@Body JsonObject userData);

    //로그인
    @POST("/users/login")
    Call<JsonObject> userLogin(@Body JsonObject userData);

    //회원가입 할 때 모든 전공이름 갖고오기
    @GET("/studentInfo/subject/majorlist")
    Call<JsonObject> registermajorlist();

    //로그인 후 자신의 정보 입력할 때 안들은 전공과목만 가져오기(post:id, major)
    @POST("/studentInfo/subject/majorlist/major")
    Call<JsonObject> majorlist(@Body JsonObject logindata);

    //로그인 후 자신의 정보 입력할 때 안들은 교양과목만 가져오기
    @POST("/studentInfo/subject/nonmajorlist")
    Call<JsonObject> nonmajorlist(@Body JsonObject logindata);

    //파이차트에 띄워줄 사용자의 현재 전공학점 가져오기
    @POST("/studentInfo/main/majorCredit")
    Call<JsonObject> majorcreditSum(@Body JsonObject userID);

    //파이차트에 띄워줄 사용자의 현재 교양학점 가져오기
    @POST("/studentInfo/main/nonmajorCredit")
    Call<JsonObject> nonmajorcreditSum(@Body JsonObject userID);

    //사용자가 들은 전공과목들 가져오기
    @POST("/studentInfo/subject/majorlist")
    Call<JsonObject> majorsubject(@Body JsonObject subjectData);

    //사용자가 들은 교양과목들 가져오기
    @POST("/studentInfo/subject/nonmajorlist")
    Call<JsonObject> nonmajorsubject(@Body JsonObject subjectData);

    //사용자의 비교과영역 졸업요건 가져오기
    @POST("/studentInfo/main/required_nonSubject")
    Call<JsonObject> nonSubject(@Body JsonObject userMajor);

    //사용자의 학기별 추천 과목들 가져오기
    @POST("/studentInfo/main/semester")
    Call<JsonObject> majorsemester(@Body JsonObject userData);

    @POST("/studentInfo/subject/completed_majorsubject")
    Call<JsonObject> completedsubject(@Body JsonObject userID);
}
