package com.example.talk5login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talk5login.Model.AuditResult;
import com.example.talk5login.Model.Data;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private Button addNewTask_btn;
    private TextView completedAudit_tv;
    private Switch inProgress_switch, notStarted_switch;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Data> dataList;
    private SwipeRefreshLayout swipeRefreshLayout;
private Realm realm;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getSupportActionBar().setTitle("Tasks");

        realm = Realm.getDefaultInstance();
        addNewTask_btn = findViewById(R.id.addNewTask_btn);
        inProgress_switch = findViewById(R.id.inProgress_switch);
        notStarted_switch = findViewById(R.id.notStarted_switch);
        completedAudit_tv = findViewById(R.id.completedAudits_link);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerViewAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        recyclerViewAdapter = new RecyclerViewAdapter(HomeActivity.this);

        completedAudit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Completed Audits clicked", Toast.LENGTH_SHORT).show();
            }
        });

        retrofitData();
        dataList = readDataFromDB();
        inProgress_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    notStarted_switch.setChecked(false);

                    ArrayList<Data> inProgressData = new ArrayList<>();
                    for (Data data: dataList){
                        if (data.getQuestionCount() > data.getQuestionCompleted() && data.getQuestionCompleted() > 0){
                            inProgressData.add(data);
                        }
                    }
                    recyclerViewAdapter.setData(inProgressData);
                }else {
                    recyclerViewAdapter.setData(dataList);
                }
            }
        });

        notStarted_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    inProgress_switch.setChecked(false);
                    ArrayList<Data> noStartedData = new ArrayList<>();
                    for (Data data: dataList){
                        if (data.getQuestionCompleted() == 0){
                            noStartedData.add(data);
                        }
                    }
                    recyclerViewAdapter.setData(noStartedData);
                }else {
                    recyclerViewAdapter.setData(dataList);
                }
            }
        });

        addNewTask_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Add new Task clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ArrayList<Data> readDataFromDB(){

        ArrayList<Data> dataListz = new ArrayList<>();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Data> dataRealmResults = realm.where(Data.class).findAll();

                for (Data data : dataRealmResults){
                    dataListz.add(data);
                }
                recyclerViewAdapter.setData(dataListz);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });
        return  dataListz;
    }
    public void retrofitData(){
        Intent intent = getIntent();
        String bearerToken = intent.getStringExtra("BearerToken");
        Log.d("sagrgarHomeAct", bearerToken);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qa-talk5api.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<AuditResult> call = jsonPlaceHolderApi.getAuditResults("Bearer " + bearerToken);

        call.enqueue(new Callback<AuditResult>() {
            @Override
            public void onResponse(Call<AuditResult> call, Response<AuditResult> response) {
                if (!response.isSuccessful()){
                    Log.d("sagrgarHomeAct", "Code: " + response.code());
                    return;
                }

//                AuditResult auditResultList = response.body();
//                for (Data data : auditResultList.getData()){
//                     dataList.add(data);
//                }

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmList<Data> dataRealmList = new RealmList<>();
                        dataRealmList.addAll(dataList);
                        realm.insertOrUpdate(dataRealmList);
                    }
                });
//                recyclerViewAdapter.setData(dataList);
//                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<AuditResult> call, Throwable t) {
                Log.d("sagrgarHomeAct", "Failed");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.talk5menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()) {
           case R.id.notification:
               Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();
                return true;

           case R.id.qr:
               Toast.makeText(this, "QR Scanner clicked", Toast.LENGTH_SHORT).show();
               return true;

           case R.id.setting:
               Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();

           default:
               return super.onOptionsItemSelected(item);
       }
    }
}