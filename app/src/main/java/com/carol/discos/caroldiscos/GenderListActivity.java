package com.carol.discos.caroldiscos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.db.GenderEntry;
import com.carol.discos.caroldiscos.adapter.GenderRecyclerViewAdapter;

import java.util.ArrayList;

public class GenderListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayout layout;
    private static final String Arquivo = "com.carol.discos.caroldiscos.COLOR";
    private int option = Color.WHITE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layout);
        backgroundColor();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenderListActivity.this, GenderEditActivity.class);
                intent.setAction(Intent.ACTION_INSERT);

                GenderListActivity.this.startActivityForResult(intent, 0);
            }
        });

        GenderDbHelper db = new GenderDbHelper(this);
        ArrayList<GenderEntry> items = db.select();
        db.close();

        mRecyclerView = (RecyclerView) findViewById(R.id.list_gender);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new GenderRecyclerViewAdapter(items, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            GenderRecyclerViewAdapter adapter = (GenderRecyclerViewAdapter) mRecyclerView.getAdapter();

            adapter.refresh();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            //about - informacoes sobre o app
            case R.id.action_about:
                Intent info = new Intent(GenderListActivity.this, InfoActivity.class);
                startActivity(info);
                return true;

            // shared preference - backgroud branco
            case R.id.action_white:
                saveColorChange(Color.WHITE);
                return true;

            //shared preference - background pink
            case R.id.action_pink:
                saveColorChange(Color.MAGENTA);
                return true;


            default:
                 return super.onOptionsItemSelected(item);
        }
    }

    private void backgroundColor(){
        SharedPreferences sharedPreferences = getSharedPreferences(Arquivo, Context.MODE_PRIVATE);

        option = sharedPreferences.getInt("color", option);

        changeColor();
    }

    private void changeColor(){
        layout.setBackgroundColor(option);
    }

    private void saveColorChange(int newValue){
        SharedPreferences sharedPreferences = getSharedPreferences(Arquivo, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("color", newValue);
        editor.commit();
        option = newValue;

        changeColor();
    }
}
