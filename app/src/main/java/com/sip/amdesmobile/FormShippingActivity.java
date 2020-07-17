package com.sip.amdesmobile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FormShippingActivity extends AppCompatActivity {

    Button btn_shippiing_simpan;
    Dialog myDialog;
    LinearLayout popup_sukses;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_shipping);
        myDialog = new Dialog(this);



        btn_shippiing_simpan = findViewById(R.id.btn_shipping_simpan);
        btn_shippiing_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                myDialog.setContentView(R.layout.popup_shipping);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                popup_sukses =(LinearLayout) myDialog.findViewById(R.id.popup_shipping_save);
                popup_sukses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(FormShippingActivity.this, ShippingActivity.class);
                        startActivity(i);
                        finish();

                    }
                });


            }
        });
    }
}
