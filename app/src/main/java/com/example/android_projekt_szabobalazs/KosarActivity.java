package com.example.android_projekt_szabobalazs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class KosarActivity extends AppCompatActivity {

    ListView kosarlista;
    ArrayList<String> arraylista = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private Toolbar toolbar;
    int osszAr;
    String ujAr;
    ArrayList<String> arLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kosar);
        initToolBar();
        loadData();

        kosarlista = findViewById(R.id.kosarLista);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraylista);
        registerForContextMenu(kosarlista);
        kosarlista.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Kosár");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(!intent.getStringExtra("ujEtel").equals(""))
        {
            String ujEtel = intent.getStringExtra("ujEtel");
            arraylista.add(ujEtel);
        }
        ujAr = intent.getStringExtra("ujAr");
        if(!intent.getStringExtra("ujAr").equals(""))
        {
            String ujAr = intent.getStringExtra("ujAr");
            arLista.add(ujAr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.listaz:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.fizet:
                intent = new Intent(this, FizetesActivity.class);
                intent.putExtra("ujOssz",szamolas());
                startActivity(intent);
                return true;
            case R.id.torles:
                Toast.makeText(this, "Lista törölve!", Toast.LENGTH_SHORT).show();
                arraylista.removeAll(arraylista);
                arLista.removeAll(arLista);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraylista);
        String json2 = gson.toJson(arLista);
        editor.putString("lista", json);
        editor.putString("arlista", json2);
        editor.apply();
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("lista", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        arraylista = gson.fromJson(json, type);
        if (arraylista == null) {
            arraylista = new ArrayList<>();
        }
        String json2 = sharedPreferences.getString("arlista", null);
        Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
        arLista = gson.fromJson(json2, type2);
        if (arLista == null) {
            arLista = new ArrayList<>();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {

            case R.id.visszavon:
                arraylista.remove(info.position);
                arLista.remove(info.position);
                adapter.notifyDataSetChanged();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    private int szamolas()
    {
        osszAr = 0;
            for (String a : arLista)
            {osszAr+= Integer.parseInt(a) + 1;
            }
            return osszAr;
    }
}