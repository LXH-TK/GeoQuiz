package com.bignerdranch.android.tk_geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mHaveSubmit;
    private boolean mHaveCheat;

    public Question(int textResId, boolean answerTrue, boolean haveSubmit) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mHaveSubmit = haveSubmit;
        mHaveCheat = false;
    }

    public boolean isHaveCheat() {
        return mHaveCheat;
    }

    public void setHaveCheat(boolean haveCheat) {
        mHaveCheat = haveCheat;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isHaveSubmit() {
        return mHaveSubmit;
    }

    public void setHaveSubmit(boolean haveSubmit) {
        mHaveSubmit = haveSubmit;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
