package com.an_mikhaylov.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bFlash;
    private FlashClass flashClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        bFlash = findViewById(R.id.b1);
        flashClass = new FlashClass(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickFlash(View view) {
        if(flashClass.isFlash_status()) {
            flashClass.flashOff();
            bFlash.setText("Включить");
            bFlash.setBackgroundResource(R.drawable.circle_green);
        } else {
            flashClass.flashOn();
            bFlash.setText("Выключить");
            bFlash.setBackgroundResource(R.drawable.circle_red);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (flashClass.isFlash_status()) {
            flashClass.flashOff();
        }
    }
}