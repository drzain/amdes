package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class GagalKirimActivity extends AppCompatActivity {

    Button btnGagal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gagal_kirim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbargagalkirim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("DRIVER");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnGagal = findViewById(R.id.simpanGagal);
        btnGagal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(GagalKirimActivity.this)
                        .setTitle("Gagal Kirim")
                        .setMessage("Data gagal pengiriman telah sukses ditambahkan dan dikirim ke PIC Pengiriman")
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
