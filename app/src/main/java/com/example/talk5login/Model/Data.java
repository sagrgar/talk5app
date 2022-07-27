package com.example.talk5login.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Data extends RealmObject {
    @SerializedName("AuditName")
    private String auditName;

    @SerializedName("QuestionCount")
    private int questionCount;

    @SerializedName("QuestionsCompleted")
    private int questionCompleted;

    @SerializedName("ID")
    private int id;

    @PrimaryKey
    @SerializedName("AuditID")
    private int auditId;


    public Data() {
    }

    public Data(String auditName, int questionCount, int questionCompleted, int id, int auditId) {
        this.auditName = auditName;
        this.questionCount = questionCount;
        this.questionCompleted = questionCompleted;
        this.id = id;
        this.auditId = auditId;
    }

    public Data(String auditName, int questionCount, int id, int auditId) {
        this.auditName = auditName;
        this.questionCount = questionCount;
        this.id = id;
        this.auditId = auditId;
    }

    public String getAuditName() {
        return auditName;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public int getId() {
        return id;
    }

    public int getAuditId() {
        return auditId;
    }

    public int getQuestionCompleted() {
        return questionCompleted;
    }

    public void setQuestionCompleted(int questionCompleted) {
        this.questionCompleted = questionCompleted;
    }

    @Override
    public String toString() {
        return "AuditResult{" +
                "auditName='" + auditName + '\'' +
                ", questionCount=" + questionCount +
                ", id=" + id +
                ", auditId=" + auditId +
                '}';
    }
}
