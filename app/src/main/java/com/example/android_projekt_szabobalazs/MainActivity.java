package com.example.android_projekt_szabobalazs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static String Etelek_URL = "https://api.onlinewebtutorblog.com/photos";
    private RecyclerView recyclerView;
    private String etelAdat;
    private List<Etel> etelLista = new ArrayList<>();
    private Toolbar toolbar;
    private ArrayAdapter<Etel> adapter;
    private RecyclerView.Adapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();
        getData();
        initToolBar();
    }

    private void initId() {
        recyclerView = findViewById(R.id.etelekLista);
    }

    private void getData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Etelek_URL).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                etelAdat = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(etelAdat);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        Etel newUser = new Etel(jsonObj.getInt("id"), jsonObj.getString("title"), jsonObj.getString("url"));
                        etelLista.add(newUser);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EtelAdapter courseAdapter = new EtelAdapter(MainActivity.this, etelLista);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(courseAdapter);
                    }
                });
            }
        });
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Étlap");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fizet:
                Intent intent = new Intent(this, KosarActivity.class);
                intent.putExtra("ujEtel","");
                intent.putExtra("ujAr","");
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}