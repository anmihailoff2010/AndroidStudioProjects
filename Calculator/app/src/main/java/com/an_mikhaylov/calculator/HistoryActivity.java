package com.an_mikhaylov.calculator;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.an_mikhaylov.calculator.db.DBHelper;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ListView lv;
    private DBHelper dbHelper;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private String calcName = "";
    private String []EmptyList = {"История пуста"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);
        if (getSupportActionBar() != null) {
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.history));
        }
        lv = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        calcName = getIntent().getStringExtra("calcName");
        list = dbHelper.showHistory(calcName);
        if(!list.isEmpty())
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        else
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,EmptyList);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void onClick(View v)
    {
        dbHelper.deleteRecords(calcName);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,EmptyList);
        lv.setAdapter(adapter);
    }
}
