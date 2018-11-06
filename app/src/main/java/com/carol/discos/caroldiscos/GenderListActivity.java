package com.carol.discos.caroldiscos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.ui.GenderRecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GenderEditActivity.class);

                MainActivity.this.startActivity(intent);
            }
        });

        GenderDbHelper db = new GenderDbHelper(this);
        ArrayList<GenderDbHelper.GenderEntry> items = db.select();
        db.close();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_gender);

        Context context = recyclerView.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new GenderRecyclerViewAdapter(items, this));
    }

}
