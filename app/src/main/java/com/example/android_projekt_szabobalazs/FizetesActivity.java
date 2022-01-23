package com.example.android_projekt_szabobalazs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FizetesActivity extends AppCompatActivity {

    TextView ossz;
    EditText cim;
    Button gomb;
    Integer ujOssz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fizetes);

        gomb = findViewById(R.id.gomb);
        ossz = findViewById(R.id.osszeg);
        cim = findViewById(R.id.lakcim);


        gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cim.getText().toString().isEmpty())
                { Toast.makeText(getApplicationContext(), "Megrendelve!", Toast.LENGTH_SHORT).show(); }
                else { Toast.makeText(getApplicationContext(), "Hibás lakcím!", Toast.LENGTH_SHORT).show(); }
            }
        });

        Intent intent = getIntent();
        ujOssz = intent.getIntExtra("ujOssz", 0) - 1;
        if (ujOssz > 0)
        {
            ossz.setText(String.valueOf(ujOssz) + ".99 Ron");
        } else ossz.setText("0 Ron (Üres lista)");

    }
}