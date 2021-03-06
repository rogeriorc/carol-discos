package com.carol.discos.caroldiscos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.carol.discos.caroldiscos.db.GenderDbHelper;
import com.carol.discos.caroldiscos.db.GenderEntry;

public class GenderEditActivity extends AppCompatActivity {

    static EditText textName;
    static EditText textDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnOk = findViewById(R.id.button_ok);

        textName = findViewById(R.id.edit_gender_name);
        textDesc = findViewById(R.id.edit_gender_desc);

        final Intent intent =  getIntent();

        if (intent.getAction() == Intent.ACTION_EDIT) {
            textName.setText(
                    intent.getStringExtra(GenderEntry.COLUMN_NAME_TITLE)
            );

            textDesc.setText(
                    intent.getStringExtra(GenderEntry.COLUMN_NAME_DESCRIPTION)
            );
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textName.getText().toString();
                String desc = textDesc.getText().toString();

                GenderDbHelper helper = new GenderDbHelper(GenderEditActivity.this);

                if (getIntent().getAction() == Intent.ACTION_INSERT) {
                    helper.insert(name, desc);
                }
                else if (intent.getAction() == Intent.ACTION_EDIT) {
                    long id = intent.getLongExtra(GenderEntry.COLUMN_NAME_ID, 0);

                    helper.update(id, name, desc);
                }

                setResult(RESULT_OK);
                finish();
            }
        });


    }

}
