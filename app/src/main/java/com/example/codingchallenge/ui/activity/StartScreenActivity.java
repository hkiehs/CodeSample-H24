package com.example.codingchallenge.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.codingchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startButton)
    public void onStartPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
