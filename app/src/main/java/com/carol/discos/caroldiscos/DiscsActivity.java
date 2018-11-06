package com.carol.discos.caroldiscos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carol.discos.caroldiscos.ui.discs.DiscsFragment;

public class DiscsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discs_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DiscsFragment.newInstance())
                    .commitNow();
        }
    }
}
