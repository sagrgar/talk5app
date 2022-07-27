package com.example.talk5login.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AuditResult extends RealmObject {

    @SerializedName("Data")
    private RealmList<Data> data;

    public AuditResult() {
    }

    public RealmList<Data> getData() {
        return data;
    }

    public void setData(RealmList<Data> data) {
        this.data = data;
    }
}
