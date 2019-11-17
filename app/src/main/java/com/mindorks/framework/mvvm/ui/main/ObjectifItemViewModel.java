package com.mindorks.framework.mvvm.ui.main;

import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;

import androidx.databinding.ObservableField;

public class ObjectifItemViewModel {

    public final ObservableField<String> created_at = new ObservableField<>();

    public final ObservableField<String> question_text = new ObservableField<>();



    public ObjectifItemViewModel(QuestionCardData questionCardData) {
        this.created_at.set(questionCardData.question.createdAt);
        this.question_text.set(questionCardData.question.questionText);
    }


    public ObjectifItemViewModel(String createdAt, String questionText) {
        this.created_at.set(createdAt);
        this.question_text.set(questionText);
    }
}
