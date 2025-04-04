package com.example.myaiapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myaiapp.R;

public class MainActivity extends AppCompatActivity {

    private Button btnGame,  btnPic, btnCh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnGame = findViewById(R.id.btnGame);
        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        btnPic = findViewById(R.id.btnPic);
        btnPic.setOnClickListener(this::onPic);

        btnCh = findViewById(R.id.btnCh);
        btnCh.setOnClickListener(this::onChat);

    }

    private void onChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private void onPic(View view) {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }


}


