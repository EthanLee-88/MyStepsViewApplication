package com.ethan.application.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mSteps;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
        mSteps = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setSteps(int mSteps) {
        this.mSteps.setValue(mSteps);
    }

    public MutableLiveData<Integer> getSteps() {
        return mSteps;
    }
}