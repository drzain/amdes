package com.sip.amdesmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LacakActivity extends AppCompatActivity
{
    Button btn_salesman_lacak;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacak);

        btn_salesman_lacak = findViewById(R.id.btn_salesman_lacak);
        btn_salesman_lacak.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LacakActivity.this, DetailLacakActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
