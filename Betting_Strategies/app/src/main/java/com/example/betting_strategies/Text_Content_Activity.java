package com.example.betting_strategies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Text_Content_Activity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView text_content;
    private Typeface face1;
    private ImageView iContent;
    private SharedPreferences def_pref;
    private int category = 0;
    private int position = 0;
    private int [] array_universal = {R.string.universal_1, R.string.universal_2, R.string.universal_3};
    private int [] array_football = {R.string.football_1, R.string.football_2, R.string.football_3};
    private int [] array_hockey = {R.string.hockey_1, R.string.hockey_2, R.string.hockey_3};
    private int [] array_auto = {R.string.auto_1, R.string.auto_2, R.string.auto_3};
    private int [] array_image_universal = {R.drawable.protivohod, R.drawable.strategiya_tankovoy_ataki, R.drawable.strategiya_stavok_na_hokkey_i_basketbol_zigzag};
    private int [] array_image_football = {R.drawable.nichya, R.drawable.expess27, R.drawable.populyarnyie_strategii_stavok_na_futbol};
    private int [] array_image_hockey = {R.drawable.stavki_na_total_v_hokkee, R.drawable.dogon_nichey_strategiya_stavok, R.drawable.stavki_na_udalenie_v_hokkee};
    private int [] array_image_auto = {R.drawable.vettel, R.drawable.stavki_na_ralli_dakar, R.drawable.stavki_na_naskar};
    private String [] array_title_universal = {"Стратегия противоход", "Стратегия танковой атаки", "Стратегия \"Зигзаг\""};
    private String [] array_title_football = {"Ставки на ничью в футболе", "Стратегия 27 экспрессов", "Популярные стратегии ставок на футбол"};
    private String [] array_title_hockey = {"Стратегии ставок на тотал в хоккее:лесенка,догон и гол на последней минуте", "Стратегия ставок на периоды в хоккее", "Ставки на удаления в хоккее"};
    private String [] array_title_auto = {"Страегия ставок на Формулу-1", "Ставки на Ралли Дакар", "Ставки на Наскар в гонках"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_content);
        init();
        reciveIntent();
    }
    private void reciveIntent() {
        Intent i = getIntent();
        if(i != null) {
            category = i.getIntExtra("category", 0);
            position = i.getIntExtra("position", 0);
        }
        switch (category) {
            case 0:
                iContent.setImageResource(array_image_universal[position]);
                text_content.setText(array_universal[position]);
                actionBar.setTitle(array_title_universal[position]);
                break;
            case 1:
                iContent.setImageResource(array_image_football[position]);
                text_content.setText(array_football[position]);
                actionBar.setTitle(array_title_football[position]);
                break;
            case 2:
                iContent.setImageResource(array_image_hockey[position]);
                text_content.setText(array_hockey[position]);
                actionBar.setTitle(array_title_hockey[position]);
                break;
            case 3:
                iContent.setImageResource(array_image_auto[position]);
                text_content.setText(array_auto[position]);
                actionBar.setTitle(array_title_auto[position]);
                break;
        }
    }
    private void init() {
        def_pref = PreferenceManager.getDefaultSharedPreferences(this);
        text_content = findViewById(R.id.text_main_content);
        iContent = findViewById(R.id.imageContent);
        face1 = Typeface.createFromAsset(this.getAssets(),"fonts/Rowdies-Light.ttf");
        text_content.setTypeface(face1);
        actionBar = getSupportActionBar();
        String text = def_pref.getString("main_text_size", "Средний");
        if(text != null) {
            switch (text) {
                case "Большой":
                    text_content.setTextSize(24);
                    break;
                case "Средний":
                    text_content.setTextSize(18);
                    break;
                case "Маленький":
                    text_content.setTextSize(14);
                    break;
            }
        }
    }
}
