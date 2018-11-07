package com.carol.discos.caroldiscos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carol.discos.caroldiscos.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle(R.string.about);
    }
}
