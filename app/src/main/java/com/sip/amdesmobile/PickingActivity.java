package com.sip.amdesmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PickingActivity extends AppCompatActivity {

    Button btnSearch;
    EditText tglpicking, numberidpicking;
    Spinner ratepicking;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpicking);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarpicking_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("Picking");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnSearch = findViewById(R.id.btnSearchPick);
        tglpicking = findViewById(R.id.tglpicking);
        numberidpicking = findViewById(R.id.numberidpicking);
        ratepicking = findViewById(R.id.ratepicking);

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

        tglpicking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PickingActivity.this, tgl, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    String tglpick = tglpicking.getText().toString();
                    String numberidpick = numberidpicking.getText().toString();
                    String ratepick = ratepicking.getSelectedItem().toString();

                    Log.e("string pick ",tglpick+" "+ratepick+" "+numberidpick);
                    Intent intent = new Intent( PickingActivity.this, ProsesPickingActivity.class);
                    intent.putExtra("tglpicking",tglpick);
                    intent.putExtra("numberidpicking",numberidpick);
                    intent.putExtra("rate",ratepick);
                    startActivity(intent);
                }catch (Exception e){
                    Log.e("exception pick ", e.getMessage());
                }

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tglpicking.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
