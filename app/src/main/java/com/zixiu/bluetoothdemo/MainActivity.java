package com.zixiu.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zixiu.bluetooth.traditional_bluetooth.TraditionalBtActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, TraditionalBtActivity.class);
        startActivity(intent);
        finish();
    }
}