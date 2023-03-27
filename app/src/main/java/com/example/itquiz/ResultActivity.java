package com.example.itquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.itquiz.databinding.ResultScoreBinding;


public class ResultActivity extends AppCompatActivity {
    private ResultScoreBinding result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.result_score);
       Bundle extra=getIntent().getExtras();
        result=DataBindingUtil.setContentView(this,R.layout.result_score);
        Toast.makeText(this, "your score", Toast.LENGTH_SHORT).show();
        result.ScoreText.setText("Your Score");
        String s=Integer.toString( extra.getInt("score"));
        result.ScoreText.setText(s);


    }


}
