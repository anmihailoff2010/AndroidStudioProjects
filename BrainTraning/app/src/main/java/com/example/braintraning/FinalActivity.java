package com.example.braintraning;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FinalActivity extends Activity {
    private SharedPreferences pref;
    private final String save_key = "save_key";
    private TextView tvTitle, tvResult, tvOldResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_layout);
        tvTitle = findViewById(R.id.tvTitle);
        tvResult = findViewById(R.id.tvResult);
        tvOldResult = findViewById(R.id.tvOldResult);
        pref = getSharedPreferences("Test", MODE_PRIVATE);
        setResult();
    }

    public void onClickFinish(View view) {
        Intent i = new Intent(FinalActivity.this,StartActivity.class);
        startActivity(i);
    }

    private void setResult() {
        String time_result = getIntent().getStringExtra("time_result");
        tvResult.setText(time_result);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(save_key,tvResult.getText().toString());
        if(!tvResult.equals(tvOldResult)) {
            tvOldResult.setText(pref.getString(save_key, "time_result"));
            editor.apply();
        }
    }
}
