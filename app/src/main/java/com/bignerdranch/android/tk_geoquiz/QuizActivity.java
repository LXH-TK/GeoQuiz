package com.bignerdranch.android.tk_geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    //TAG常量可以过滤日志输出
    private static final String TAG = "QuizActivity";

    //存储键值对于Bundle中，然后取回，保存mCurrentIndex的值
    private static final String KEY_INDEX = "index";
    private static final String KEY_CORRECT = "correct";

    private static final int REQUEST_CODE_CHEAT = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false)
    };

    private int mCurrentIndex = 0;
    private int mCorrectNumber = 0;

    private static final String[] KEY_BOOL = {"bool1", "bool2", "bool3", "bool4", "bool5", "bool6"};
    private static final String[] KEY_CHEAT = {"cheat1", "cheat2", "cheat3", "cheat4", "cheat5", "cheat6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //日志输出
        Log.i(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCorrectNumber = savedInstanceState.getInt(KEY_CORRECT, 0);
            for (int i = 0; i < mQuestionBank.length; i++) {
                mQuestionBank[i].setHaveSubmit(savedInstanceState.getBoolean(KEY_BOOL[i], false));
                mQuestionBank[i].setHaveCheat(savedInstanceState.getBoolean(KEY_CHEAT[i], false));
            }
        }
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(QuizActivity.this,
                                R.string.correct_toast,
                                Toast.LENGTH_SHORT).show();
                */
                if (checkAnswer(true, mQuestionBank[mCurrentIndex].isHaveCheat()) == true)
                    mCorrectNumber ++;
                mQuestionBank[mCurrentIndex].setHaveSubmit(true);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(QuizActivity.this,
                                R.string.incorrect_toast,
                                Toast.LENGTH_SHORT).show();
                */
                if (checkAnswer(false, mQuestionBank[mCurrentIndex].isHaveCheat()) == true)
                    mCorrectNumber ++;
                mQuestionBank[mCurrentIndex].setHaveSubmit(true);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }
        });


        mPrevButton = findViewById(R.id.prev_button);
        mNextButton = findViewById(R.id.next_button);

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex > 0)
                    mCurrentIndex = mCurrentIndex - 1;
                else
                    mCurrentIndex = mQuestionBank.length - 1;

                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                updateQuestion();
            }
        });


        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    //重写onActivityResult()方法接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null)
                return;
            mQuestionBank[mCurrentIndex].setHaveCheat(CheatActivity.wasAnswerShown(data));
        }
    }

    /*
    为生命周期方法添加日志输出代码
    首先调用超类实现方法，然后再调用其他方法
    使用@Override注解要求编译器保证当前类拥有自己想覆盖的方法
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() called");
    }

    //覆盖onSaveInstanceState方法
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "OnSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_CORRECT, mCorrectNumber);
        for (int i = 0; i < mQuestionBank.length; i++) {
            savedInstanceState.putBoolean(KEY_BOOL[i], mQuestionBank[i].isHaveSubmit());
            savedInstanceState.putBoolean(KEY_CHEAT[i], mQuestionBank[i].isHaveCheat());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        if (mQuestionBank[mCurrentIndex].isHaveSubmit() == false) {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
        else {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }

        if (haveFinish() == true) {
            mPrevButton.setEnabled(false);
            mNextButton.setEnabled(false);
            mQuestionTextView.setEnabled(false);
            mCheatButton.setEnabled(false);
            showScore();
        }
    }

    private boolean checkAnswer(boolean userPressedTrue, boolean isCheat) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(isCheat)
            messageResId = R.string.judgment_toast;
        else {
            if(userPressedTrue == answerIsTrue)
                messageResId = R.string.correct_toast;
            else
                messageResId = R.string.incorrect_toast;
        }

        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 200);
        toast.show();

        if (messageResId == R.string.correct_toast)
            return true;
        else
            return false;
    }

    private boolean haveFinish() {
        for (int i=0;i<mQuestionBank.length;i++)
            if(mQuestionBank[i].isHaveSubmit() == false)
                return false;
        return true;
    }

    private void showScore() {
        float score = (float)mCorrectNumber / (float)mQuestionBank.length;
        Toast toast = Toast.makeText(this, "You Have Finished And Got " + score + " Correctness!", Toast.LENGTH_LONG);
        toast.show();
    }
}
