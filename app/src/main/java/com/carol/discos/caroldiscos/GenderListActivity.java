package com.carol.discos.caroldiscos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.ui.GenderRecyclerViewAdapter;

import java.util.ArrayList;

public class GenderListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        ArrayList<GenderDbHelper.GenderEntry> items = db.select();
        db.close();

        mRecyclerView = (RecyclerView) findViewById(R.id.list_gender);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new GenderRecyclerViewAdapter(items, this));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            GenderRecyclerViewAdapter adapter = (GenderRecyclerViewAdapter) mRecyclerView.getAdapter();

            adapter.refresh();
        }
    }

}
