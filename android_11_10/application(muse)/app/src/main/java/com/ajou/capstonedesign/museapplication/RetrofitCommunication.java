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
    @POST("/users/register")
    Call<JsonObject> userRegister(@Body JsonObject userData);

    @POST("/users/login")
    Call<JsonObject> userLogin(@Body JsonObject userData);

    @GET("/studentInfo/subject/majorlist")
    Call<JsonObject> regisetermajorlist();



}
