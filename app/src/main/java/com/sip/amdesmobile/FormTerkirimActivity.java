package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class FormTerkirimActivity extends AppCompatActivity {

    Button btnTerkirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_terkirim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarformterkirim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("DRIVER");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnTerkirim = findViewById(R.id.simpanTerkirim);
        btnTerkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FormTerkirimActivity.this)
                        .setTitle("Proses Pengiriman")
                        .setMessage("Proses pengiriman telah sukses dilakukan")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //kirimData(nomorid,userid);
                                //Log.e("lempar kirim ","coba error engga");
                            }}).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
