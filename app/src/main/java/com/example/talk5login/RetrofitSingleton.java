package com.example.talk5login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static final String BASE_URL = "https://qa-talk5api.azurewebsites.net";
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    public static  JsonPlaceHolderApi getJsonPlaceHolderApi() {
        return jsonPlaceHolderApi;
    }

}
