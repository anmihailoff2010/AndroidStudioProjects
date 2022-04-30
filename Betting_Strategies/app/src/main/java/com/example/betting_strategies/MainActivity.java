package com.example.betting_strategies;

import android.content.Intent;
import android.os.Bundle;

import com.example.betting_strategies.settings.SettingActivity;
import com.example.betting_strategies.utils.CustomArrayAdapter;
import com.example.betting_strategies.utils.ListItemClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView list;
    private String[] array;
    private CustomArrayAdapter adapter;
    private Toolbar toolbar;
    private int category_index;
    private int[] array_image_universal = new int[]{R.drawable.protivohod,R.drawable.strategiya_tankovoy_ataki,R.drawable.strategiya_stavok_na_hokkey_i_basketbol_zigzag};
    private int[] array_image_football = new int[]{R.drawable.nichya,R.drawable.expess27,R.drawable.populyarnyie_strategii_stavok_na_futbol};
    private int[] array_image_hockey = new int[]{R.drawable.stavki_na_total_v_hokkee,R.drawable.dogon_nichey_strategiya_stavok,R.drawable.stavki_na_udalenie_v_hokkee};
    private int[] array_image_auto = new int[]{R.drawable.vettel,R.drawable.stavki_na_ralli_dakar,R.drawable.stavki_na_naskar};
    private List<ListItemClass> listItemMain;
    private ListItemClass listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = findViewById(R.id.listView);
        listItemMain = new ArrayList<>();

        fillArray(R.string.universal, getResources().getStringArray(R.array.universal_array),array_image_universal, 0);

        adapter = new CustomArrayAdapter(this,R.layout.list_view_item_1,listItemMain,getLayoutInflater());
        list.setAdapter(adapter);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Text_Content_Activity.class);
                intent.putExtra("category", category_index);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        toolbar.setTitle(R.string.universal);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.id_universal) {
            fillArray(R.string.universal, getResources().getStringArray(R.array.universal_array),array_image_universal, 0);

        } else if (id == R.id.id_football) {
            fillArray(R.string.football, getResources().getStringArray(R.array.football_array),array_image_football, 1);

        } else if (id == R.id.id_hockey) {
            fillArray(R.string.hockey, getResources().getStringArray(R.array.hockey_array),array_image_hockey, 2);

        } else if (id == R.id.id_auto) {
            fillArray(R.string.auto, getResources().getStringArray(R.array.auto_array),array_image_auto, 3);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void fillArray(int title,String[] nameArray,int[] image, int index) {
        toolbar.setTitle(title);
        if(adapter != null) adapter.clear();
        for(int i = 0; i < nameArray.length; i++) {
            listItem = new ListItemClass();
            listItem.setNamed(nameArray[i]);
            listItem.setImage_id(image[i]);
            listItemMain.add(listItem);
        }
        if(adapter != null) adapter.notifyDataSetChanged();
        category_index = index;
    }
}
