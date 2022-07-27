package com.example.talk5login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {


    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);

        Intent intent =getIntent();
        String url =  intent.getStringExtra("url");

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
       // Log.d("sagrgarWEBVIEW", url);
    }
}