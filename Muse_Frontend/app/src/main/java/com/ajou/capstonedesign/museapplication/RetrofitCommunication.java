package com.ajou.capstonedesign.museapplication;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

    //전공이랑 학번 보내줘야함
    @POST("/studentInfo/main/required_majorCredit")
    Call<JsonObject> requiredmajorcredit(@Body JsonObject userData);

    //파이차트에 띄워줄 사용자의 현재 교양학점 가져오기
    @POST("/studentInfo/main/nonmajorCredit")
    Call<JsonObject> nonmajorcreditSum(@Body JsonObject userID);

    //전공이랑 학번 보내줘야함
    @POST("/studentInfo/main/required_nonmajorCredit")
    Call<JsonObject> requirednonmajorcredit(@Body JsonObject userData);

    //사용자가 들은 전공과목들 선택 후 서버에 반영
    @POST("/studentInfo/subject/majorlist")
    Call<JsonObject> majorsubject(@Body JsonObject subjectData);

    //사용자가 들은 교양과목들 선택 후 반영
    @POST("/studentInfo/subject/StudentNonmajorlist")
    Call<JsonObject> nonmajorsubject(@Body JsonObject subjectData);

    //사용자의 비교과영역 졸업요건 가져오기
    @POST("/studentInfo/main/required_nonSubject")
    Call<JsonObject> nonSubject(@Body JsonObject userMajor);

    //사용자의 학기별 추천 과목들 가져오기
    @POST("/studentInfo/main/semester")
    Call<JsonObject> majorsemester(@Body JsonObject userData);

    //사용자가 입력한 토익점수 서버에 반영하기
    @POST("/studentInfo/main/languageScore")
    Call<JsonObject> languagescore(@Body JsonObject toeicscore);

    //사용자가 입력한 토익접수 서버에서 받아와서 띄워주기
    @POST("/studentInfo/main/mainLanguageScore")
    Call<JsonObject> userscore(@Body JsonObject userID);

    //사용자의 아이디와 옵션 두가지를 보내면 추천 시간표를 보내준다
    @POST("/timeTable/timeTable/time")
    Call<JsonObject> timetable(@Body JsonObject userData);

    //사용자의 데이터에서 들은 전공 데이터만 받아오기(id 보내주기)
    @POST("/studentInfo/subject/completed_majorsubject")
    Call<JsonObject> completedmajorsubject(@Body JsonObject userID);

    //사용자의 데이터에서 들은 교양 데이터만 받아오기(id 보내주기)
    @POST("/studentInfo/subject/completed_nonmajorsubject")
    Call<JsonObject> compltednonmajorsubject(@Body JsonObject userID);

    //사용자가 수정하고 싶은 전공 과목을 보내면 데이터베이스에서 삭제해주기
    //@HTTP(method = "DELETE", path = "/studentInfo/subject/deleteMajorlist", hasBody = true)
    @POST("/studentInfo/subject/deleteMajor")
    Call<JsonObject> deletemajor(@Body JsonObject subjectData);

    //사용자가 수정하고 싶은 교양 과목을 보내면 데이터베이스에서 삭제해주기
    //@HTTP(method = "DELETE", path = "/studentInfo/subject/deleteNonmajorlist", hasBody = true)
    @POST("/studentInfo/subject/deleteNonmajor")
    Call<JsonObject> deletenonmajor(@Body JsonObject subjectData);

}
