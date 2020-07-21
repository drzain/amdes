package com.sip.amdesmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

import okhttp3.Route;

public class RouteActivity extends AppCompatActivity {

    Button btnSearchroute;
    EditText tglroute, numberidroute;
    Spinner rateroute;
    final Calendar myCalendar = Calendar.getInstance();
    private SessionManager session;
    DatePickerDialog.OnDateSetListener tgl;
    FloatingActionButton btnKeluar;
    String namauser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarroute);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarroute_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("Route");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnSearchroute = findViewById(R.id.buttonRoute);
        tglroute = findViewById(R.id.tglroute);
        numberidroute = findViewById(R.id.numberidroute);
        rateroute = findViewById(R.id.rateroute);
        btnKeluar = findViewById(R.id.btn_keluar_route);

        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

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

        tglroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RouteActivity.this, tgl, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearchroute = findViewById(R.id.buttonRoute);

        btnSearchroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String tglrou = tglroute.getText().toString();
                    String numberidrou = numberidroute.getText().toString();
                    String raterou = rateroute.getSelectedItem().toString();

                    Log.e("string pick ",tglrou+" "+raterou+" "+numberidrou);
                    Intent intent = new Intent( RouteActivity.this, ProsesRouteActivity.class);
                    intent.putExtra("tglroute",tglrou);
                    intent.putExtra("numberidroute",numberidrou);
                    intent.putExtra("rate",raterou);
                    startActivity(intent);
                }catch (Exception e){
                    Log.e("exception route ", e.getMessage());
                }
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                Intent intent = new Intent( RouteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tglroute.setText(sdf.format(myCalendar.getTime()));
    }
}
