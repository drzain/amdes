package com.sip.amdesmobile;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SalesmanActivity extends AppCompatActivity
{

    Button btn_salesman_search;
    private SessionManager session;;
    FloatingActionButton btn_keluar;
    String namauser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman);
        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

        btn_salesman_search = findViewById(R.id.btn_salesman_search);
        btn_salesman_search.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
            Intent intent = new Intent(SalesmanActivity.this, LacakActivity.class);
            startActivity(intent);
            finish();
            }

        });

        btn_keluar = findViewById(R.id.btn_keluar_salesman);
        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                Intent intent = new Intent( SalesmanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
