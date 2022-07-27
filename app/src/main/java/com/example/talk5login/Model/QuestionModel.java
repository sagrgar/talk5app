package com.example.talk5login.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class QuestionModel {

    @SerializedName("Data")
    private List<QuestionData> questionData;

    public QuestionModel() {
    }

    public List<QuestionData> getQuestionData() {
        return questionData;
    }

    public void setQuestionData(List<QuestionData> questionData) {
        this.questionData = questionData;
    }
}
