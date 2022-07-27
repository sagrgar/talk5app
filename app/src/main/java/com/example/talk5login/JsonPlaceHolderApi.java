package com.example.talk5login;

import com.example.talk5login.Model.AuditResult;
import com.example.talk5login.Model.Authentication;
import com.example.talk5login.Model.QuestionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("/api/mobile/auth")
    Call<Authentication> createPost(@Body Authentication authentication);

    @GET("/api/mobile/sync?fromversionid=0&entityname=auditresultheader")
    Call<AuditResult> getAuditResults(@Header("Authorization") String bearerToken);

    @GET("/api/mobile/sync?fromversionid=0&entityname=auditresult")
    Call<QuestionModel> getQuestions(@Header("Authorization") String bearerToken);


}
