package com.sip.amdesmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShippingActivity extends AppCompatActivity
{
    Button btn_shipping_src;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        btn_shipping_src = findViewById(R.id.btn_shipping_search);
        btn_shipping_src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent( ShippingActivity.this, FormShippingActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
