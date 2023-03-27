package com.example.itquiz;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.itquiz.databinding.QuizQuestionBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class QuizActivity extends MainActivity{
    private int currentQuestionid=0;
    private boolean useranswer;
    protected int score=0;

    QuizQuestionBinding quiz;
    private final Question[] question = new Question[]{
            new Question(R.string.Question1,false),
            new Question(R.string.Question2,false),
            new Question(R.string.Question3,true),
            new Question(R.string.Question4,true),
            new Question(R.string.Question5,true),
            new Question(R.string.Question6,false),
            new Question(R.string.Question7,true),
            new Question(R.string.Question8,true),
            new Question(R.string.Question9,true),
            new Question(R.string.Question10,false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question);
        quiz=DataBindingUtil.setContentView(this,R.layout.quiz_question);
        quiz.QuestionText.setText(question[currentQuestionid].getQuestionId());

        quiz.FalseButton.setOnClickListener(view -> {
            checkanswer(false);
        });
        quiz.TrueButton.setOnClickListener(view -> {
            checkanswer(true);
        });
        quiz.PreviousButton.setOnClickListener(view -> {
            if(currentQuestionid>0 && currentQuestionid<10){
                currentQuestionid--;
                quiz.QuestionText.setText(question[currentQuestionid].getQuestionId());

            }
        });
        quiz.NextButton.setOnClickListener(view -> {
            if(currentQuestionid>=0 && currentQuestionid<9 ){
                currentQuestionid++;
                quiz.QuestionText.setText(question[currentQuestionid].getQuestionId());

            };
        });
        quiz.SubmitButton.setOnClickListener(view -> {
            Intent intent1=new Intent(QuizActivity.this, ResultActivity.class);
            intent1.putExtra("score",score);
            startActivity(intent1);

        });
    }

    public void checkanswer(boolean useranswer){
        boolean answer=question[currentQuestionid].isAnsId();
        int masssgeid;
        if (useranswer==answer){
            masssgeid=R.string.correct;
            score++;
            currentQuestionid++;
            quiz.QuestionText.setText(question[currentQuestionid].getQuestionId());

        }
        else
            masssgeid=R.string.incorrect;


        Snackbar.make(quiz.QuestionText,masssgeid, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
