package com.example.talk5login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talk5login.Model.Authentication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText username_et, password_et;
    private Button login_btn;

    private TextView legal_tv, faq_tv, contact_tv;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);

        legal_tv = findViewById(R.id.legalLink_tv);
        faq_tv = findViewById(R.id.faqLink_tv);
        contact_tv = findViewById(R.id.contactLink_tv);

        login_btn = findViewById(R.id.login_btn);



        dummyLinks();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://qa-talk5api.azurewebsites.net")
//                        .addConverterFactory(GsonConverterFactory.create())
//                                .build();
//
//        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        jsonPlaceHolderApi = RetrofitSingleton.getJsonPlaceHolderApi();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_et.getText().toString();
                String password = password_et.getText().toString();

                Authentication authentication = new Authentication(username, password, "password");
                Call<Authentication> call = jsonPlaceHolderApi.createPost(authentication);
                call.enqueue(new Callback<Authentication>() {
                    @Override
                    public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Failed" + username + password, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Authentication authenticationResponse = response.body();
                        Log.d("sagrgar", authenticationResponse.getToken());

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("BearerToken", authenticationResponse.getToken());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Authentication> call, Throwable t) {

                    }
                });

            }
        });
    }

    public void dummyLinks() {

        legal_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://talk5.app/Home/Legal");
                startActivity(intent);

            }
        });

        faq_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://talk5.app/Home/FAQs");
                startActivity(intent);
            }
        });

        contact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://talk5.app/Home/GetinTouch");
                startActivity(intent);
            }
        });

    }
}