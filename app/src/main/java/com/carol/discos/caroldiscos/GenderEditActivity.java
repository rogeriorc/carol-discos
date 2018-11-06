package com.carol.discos.caroldiscos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.carol.discos.caroldiscos.db.GenderDbHelper;

public class GenderEditActivity extends AppCompatActivity {

    static EditText textName;
    static EditText textDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnOk = findViewById(R.id.button_ok);

        textName = findViewById(R.id.edit_gender_name);
        textDesc = findViewById(R.id.edit_gender_desc);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textName.getText().toString();
                String desc = textDesc.getText().toString();

                GenderDbHelper helper = new GenderDbHelper(GenderEditActivity.this);

                helper.insert(name, desc);

                GenderEditActivity.this.finish();

          }
        });


    }

}
