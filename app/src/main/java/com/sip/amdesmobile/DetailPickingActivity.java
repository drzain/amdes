package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DetailPickingActivity extends AppCompatActivity {

    Button btnPicking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_picking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetpicking);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Picking");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnPicking = findViewById(R.id.btndetPick);

        btnPicking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailPickingActivity.this)
                        .setTitle("Proses Picking")
                        .setMessage("Proses Konfirmasi Picking Sukses Dilakukan")
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
