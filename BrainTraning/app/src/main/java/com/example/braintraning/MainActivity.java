package com.example.braintraning;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private TextView tvMain, tvRes;
    private ActionBar actionBar;
    private int number_1;
    private int number_2;
    private int number_false;
    private int number_index;
    private int number_res;
    private int max = 20;
    private int min = 1;
    private int max1 = 40;
    private int min1 = 10;
    private long startTime = 0;
    private long currentTime = 0;
    private float time_result = 0.0f;
    private int true_answer = 0;
    private int max_true_answer = 10;
    private boolean is_true_answer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        startTime = System.currentTimeMillis();
        tvMain = findViewById(R.id.tvMain);
        tvRes = findViewById(R.id.tvRes);
        tvRes.setText(String.valueOf(true_answer));
        actionBar = getSupportActionBar();
        numbers();
    }

    private void numbers() {
        number_1 = (int) (Math.random() * (max - min));
        number_2 = (int) (Math.random() * (max - min));
        number_false = (int) (Math.random() * (max1 - min1));
        number_index = (int) (Math.random() * (5 - 1));
        number_res = number_1 + number_2;
        String text;
        if(number_index == 3 || number_index == 1 || number_res == number_false) {
            text = number_1 + "+" + number_2 + "=" + number_res;
            is_true_answer = true;
        } else {
            text = number_1 + "+" + number_2 + "=" + number_false;
            is_true_answer = false;
        }
        tvMain.setText(text);
        if(true_answer >= max_true_answer) {
            Intent i = new Intent(MainActivity.this,FinalActivity.class);
            i.putExtra("time_result", actionBar.getTitle().toString());
            startActivity(i);

        }
    }

    public void onClickTrue(View view) {
        if (is_true_answer) {
            true_answer++;
            numbers();
            currentTime = System.currentTimeMillis();
            time_result = (float) (currentTime - startTime) / 1000;
            String time = "Time: " + time_result;
            actionBar.setTitle(time);
        } else {
            numbers();
            currentTime = System.currentTimeMillis();
            time_result = (float) (currentTime - startTime) / 1000;
            String time = "Time: " + time_result;
            actionBar.setTitle(time);
        }
        tvRes.setText(String.valueOf(true_answer));
    }

    public void onClickFalse(View view) {
        if (!is_true_answer) {
            true_answer++;
            numbers();
            currentTime = System.currentTimeMillis();
            time_result = (float) (currentTime - startTime) / 1000;
            String time = "Time: " + time_result;
            actionBar.setTitle(time);
        } else {
            numbers();
            currentTime = System.currentTimeMillis();
            time_result = (float) (currentTime - startTime) / 1000;
            String time = "Time: " + time_result;
            actionBar.setTitle(time);
        }
        tvRes.setText(String.valueOf(true_answer));
    }
}