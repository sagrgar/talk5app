package com.example.talk5login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.talk5login.Model.QuestionData;
import com.example.talk5login.Model.QuestionModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private int auditResultHeaderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().setTitle("Questions");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        String bearer = HomeActivity.BEARER_TOKEN;

        Intent intent = getIntent();
        auditResultHeaderID = intent.getIntExtra("id", -1);

   //     Log.d("sagrgarQuestion", auditResultHeaderID + bearer );

//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://qa-talk5api.azurewebsites.net").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = RetrofitSingleton.getJsonPlaceHolderApi();

        Call<QuestionModel> questionModelCall = jsonPlaceHolderApi.getQuestions("Bearer " + bearer);

        questionModelCall.enqueue(new Callback<QuestionModel>() {
            @Override
            public void onResponse(Call<QuestionModel> call, Response<QuestionModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("sagrgarQyestionAct", "Code: " + response.code());
                    return;
                }
                QuestionModel questionList = response.body();
                for (QuestionData questionData : questionList.getQuestionData()){
                    if (questionData.getHeaderID() == auditResultHeaderID){
                        Log.d("sagrgarQyestionAct", questionData.getQuestion() + "?");

                    }


                }
            }

            @Override
            public void onFailure(Call<QuestionModel> call, Throwable t) {

            }
        });

    }
}