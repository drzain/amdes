package com.sip.amdesmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SalesmanActivity extends AppCompatActivity
{

    Button btn_salesman_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman);

        btn_salesman_search = findViewById(R.id.btn_salesman_search);

        btn_salesman_search.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
            Intent intent = new Intent(SalesmanActivity.this, LacakActivity.class);
            startActivity(intent);
            finish();
            }

        });


    }
}
