package com.sip.amdesmobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class ShippingActivity extends AppCompatActivity
{
    Button btn_shipping_src;
    EditText tglshipping, jamshipping;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener tgl;
    TimePickerDialog picker;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarshipping);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarshipping_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("Shipping");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        tglshipping = findViewById(R.id.tglshipping);
        tglshipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(
                        ShippingActivity.this, tgl,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tgl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        jamshipping = findViewById(R.id.jamshipping);
        jamshipping.setInputType(InputType.TYPE_NULL);
        jamshipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               final Calendar cldr = Calendar.getInstance();
               int hour = cldr.get(Calendar.HOUR_OF_DAY);
               int minutes = cldr.get(Calendar.MINUTE);
               picker = new TimePickerDialog(ShippingActivity.this,
                       new TimePickerDialog.OnTimeSetListener()
                       {
                           @Override
                           public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                           {
                               jamshipping.setText(hourOfDay + ":" + minute);

                           }
                       }, hour, minutes, true);
                    picker.show();
            }
        });


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
    private void updateLabel()
    {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tglshipping.setText(sdf.format(myCalendar.getTime()));
    }
}
