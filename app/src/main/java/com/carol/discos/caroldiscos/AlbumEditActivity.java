package com.carol.discos.caroldiscos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.carol.discos.caroldiscos.db.AlbumDbHelper;
import com.carol.discos.caroldiscos.db.AlbumEntry;
import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.db.GenderEntry;

import java.util.ArrayList;
import java.util.List;

public class AlbumEditActivity extends AppCompatActivity {

    static EditText textName;
    static EditText textArtist;
    static EditText textYear;
    static Spinner spinnerGenre;
    List<GenderEntry> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnOk = findViewById(R.id.button_ok);

        textName = findViewById(R.id.edit_album_name);
        textArtist = findViewById(R.id.edit_album_artist);
        textYear = findViewById(R.id.edit_album_year);
        spinnerGenre = findViewById(R.id.spinner_album_genre);

        addItemsOnSpinner();

        final Intent intent = getIntent();

        if (intent.getAction() == Intent.ACTION_EDIT) {
            String title = intent.getStringExtra(AlbumEntry.COLUMN_NAME_TITLE);
            String artist = intent.getStringExtra(AlbumEntry.COLUMN_NAME_ARTIST);
            int year = intent.getIntExtra(AlbumEntry.COLUMN_NAME_YEAR, 0);
            int genre = 0;

            for (GenderEntry e : list) {
                if (e.id == intent.getLongExtra(AlbumEntry.COLUMN_NAME_GENRE, 0)) {
                    genre = list.indexOf(e);
                }
            }

            textName.setText(title);
            textArtist.setText(artist);
            textYear.setText(String.valueOf(year));
            spinnerGenre.setSelection(genre);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textName.getText().toString();
                String desc = textArtist.getText().toString();
                int year = Integer.valueOf(textYear.getText().toString());
                long genre = ((GenderEntry) spinnerGenre.getSelectedItem()).id;

                AlbumDbHelper helper = new AlbumDbHelper(AlbumEditActivity.this);

                if (getIntent().getAction() == Intent.ACTION_INSERT) {
                    helper.insert(name, desc, year, genre);
                } else if (intent.getAction() == Intent.ACTION_EDIT) {
                    long id = intent.getLongExtra(AlbumEntry.COLUMN_NAME_ID, 0);

                    helper.update(id, name, desc, year, genre);
                }

                setResult(RESULT_OK);
                finish();
            }
        });


    }

    public void addItemsOnSpinner() {
        GenderDbHelper db = new GenderDbHelper(this);
        this.list = db.select();

        list.add(0, new GenderEntry());

        ArrayAdapter<GenderEntry> dataAdapter = new ArrayAdapter<GenderEntry>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(dataAdapter);

        db.close();
    }

}