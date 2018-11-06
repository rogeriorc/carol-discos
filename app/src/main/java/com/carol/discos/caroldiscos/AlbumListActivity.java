package com.carol.discos.caroldiscos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.carol.discos.caroldiscos.adapter.AlbumRecyclerViewAdapter;
import com.carol.discos.caroldiscos.db.AlbumDbHelper;
import com.carol.discos.caroldiscos.db.AlbumEntry;

import java.util.ArrayList;

public class AlbumListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlbumListActivity.this, AlbumEditActivity.class);
                intent.setAction(Intent.ACTION_INSERT);

                AlbumListActivity.this.startActivityForResult(intent, 0);
            }
        });

        AlbumDbHelper db = new AlbumDbHelper(this);
        ArrayList<AlbumEntry> items = db.select();
        db.close();

        mRecyclerView = (RecyclerView) findViewById(R.id.list_album);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AlbumRecyclerViewAdapter(items, this));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            AlbumRecyclerViewAdapter adapter = (AlbumRecyclerViewAdapter) mRecyclerView.getAdapter();

            adapter.refresh();
        }
    }

}
