package com.an_mikhaylov.courseofcurrencies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.google.gson.JsonParser.parseReader;
import static com.google.gson.JsonParser.parseString;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_VALUTE = "Valute";
    private static final int MSG_UPDATE_NONE = 0;
    private static final int MSG_UPDATE_IN_PROGRESS = 1;
    private static final int MSG_UPDATE_COMPLETED = 2;
    private final String currencies = "https://www.cbr-xml-daily.ru/daily_json.js";
    protected static List<Valute> valuteList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    private String jsonDateString;
    private String timestampString;

    private boolean updateInProgress;

    private TextView infoTextView;
    private TextView dateTextView;
    private TextView timestampTextView;
    private ProgressBar progressBar;
    private long startTime;

    RecyclerView valuteRecycler;
    ValuteAdapter valuteAdapter;

    private Handler mHandler;

    private void updateData() {
        Thread secThread = new Thread(updater);
        secThread.start();

    }

    private Runnable updater = new Runnable(){
        @Override
        public void run() {
            parseJsonToValutesList(readJsonDataFromWeb(currencies));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency);

        infoTextView = findViewById(R.id.infoTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timestampTextView = findViewById(R.id.timestampTextView);
        progressBar = findViewById(R.id.progressbar);

        setValuteRecycler(valuteList);

        Button updateButton = findViewById(R.id.updateButton);

        GetURLData getURLData = new GetURLData();
        getURLData.execute(currencies);

        startTime = System.currentTimeMillis();
        mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case MSG_UPDATE_NONE:
                        updateButton.setEnabled(true);
                        updateButton.setVisibility(View.VISIBLE);
                        break;
                    case MSG_UPDATE_IN_PROGRESS:
                        updateButton.setEnabled(false);
                        updateButton.setAlpha(0.1f);
                        valuteRecycler.setAlpha(0.5f);
                        infoTextView.setVisibility(View.VISIBLE);
                        infoTextView.setText("???????????????????? ????????????: ...");
                        progressBar.setVisibility(View.VISIBLE); // ???????????????????? ?????????????????? ??????????????????
                        break;
                    case MSG_UPDATE_COMPLETED:
                        updateButton.setEnabled(true);
                        updateButton.setAlpha(1f);
                        valuteRecycler.setAlpha(1f);
                        infoTextView.setText("???????????????????? ??????????????????");
                        infoTextView.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE); // ???????????? ?????????????????????? ??????????????????
                        dateTextView.setText(jsonDateString);
                        timestampTextView.setText(timestampString);
                        valuteAdapter.notifyDataSetChanged();
                        break;
                }
                mHandler.sendEmptyMessage(MSG_UPDATE_NONE);
                return true;
            }
        });

        Timer myTimer = new Timer(); // ?????????????? ????????????

        myTimer.schedule(new TimerTask() { // ???????????????????? ????????????
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_UPDATE_IN_PROGRESS);
                Log.d("MyLog", "Scheduled task started every 10 s... ");
                updateData();
                mHandler.sendEmptyMessage(MSG_UPDATE_COMPLETED);
            };
        }, 0L, 15 * 60L * 1000); // ???????????????? - 60 000 ??????????????????????, 0 ?????????????????????? ???? ?????????????? ??????????????.

        updateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(MSG_UPDATE_IN_PROGRESS);
                Log.d("MyLog", "update button clicked... " + mHandler.obtainMessage());
                updateData();
                try {
                    takePause(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_UPDATE_COMPLETED);
            }
        });
    }

    protected void setValuteRecycler(List<Valute> valuteList) {
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        valuteRecycler = findViewById(R.id.valuteRecycler);
        valuteRecycler.setLayoutManager(layoutManager);
        valuteAdapter = new ValuteAdapter(this, valuteList);
        valuteRecycler.setAdapter(valuteAdapter);
    }

    private class GetURLData extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            parseJsonToValutesList(readJsonDataFromWeb(strings[0]));
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mHandler.sendEmptyMessage(MSG_UPDATE_COMPLETED);
            valuteAdapter.notifyDataSetChanged();
        }
    }

    protected JsonArray readJsonDataFromWeb(String link){
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        String[] res;

        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try (InputStream in = url.openStream()) {

            JsonElement root = parseReader(new BufferedReader(new InputStreamReader(in)));
            jsonObject = root.getAsJsonObject();

            jsonDateString = "???? : " + jsonObject.get("Date").toString().substring(1, 11);
            timestampString = "?????????????????? ????????????????????: " + "\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("H:mm:ss  dd-MM-yyyy"));

            String val = jsonObject.get(TAG_VALUTE).toString().substring(1, jsonObject.get(TAG_VALUTE).toString().length() - 1);
            res = val.split("\\},");
            for (int i = 0; i < res.length; i++) {      //add  JSON objects to JSON array from strings
                if (i < res.length - 1) {
                    res[i] = res[i].substring(6) + "}";
                } else {
                    res[i] = res[i].substring(6);
                }
                jsonArray.add(parseString(res[i]).getAsJsonObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("MyLog", jsonArray.size() + " elements of JsonArray created");
        return jsonArray;

    }

    protected void parseJsonToValutesList(JsonArray jsonArray){
        valuteList.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject obj = (JsonObject) jsonArray.get(i);
            valuteList.add(new Valute(
                    i,
                    obj.get("CharCode").getAsString(),
                    obj.get("Nominal").getAsInt(),
                    obj.get("Name").getAsString(),
                    obj.get("Value").getAsDouble(),
                    obj.get("Previous").getAsDouble()));
            try {
                takePause(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("MyLog", " list of " + valuteList.size() + " valutes created by createValuteList...");
    }

    private void takePause(int step) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(step);
    }
}