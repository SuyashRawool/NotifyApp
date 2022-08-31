package com.example.showtext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout containerRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        containerRL = findViewById(R.id.constLayout1);
        containerRL.setBackground(getResources().getDrawable(R.drawable.blue_rasp_bg));

        Button begin = findViewById(R.id.beginButton);
        begin.setOnClickListener((view)->{
            Intent mainIntent = new Intent(this,TextActivity.class);

            startActivity(mainIntent);
        });
    }
}