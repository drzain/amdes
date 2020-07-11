package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailPickingActivity extends AppCompatActivity {

    Button btnPicking;
    AlertDialog dialog;
    String numberid,nokaid,clr,datarow,posisi;
    TextView txtdetrowPick, detpicktgl,detpickrate,detpicknama,detpicksales,detpicknumberid,detpicknokaid,detpickclr,detpickfloc,detpickacc,detpickcatatan,detpickwilayah;
    private static final String TAG = DetailPickingActivity.class.getSimpleName();
    String nomor_id,tgl,ratepick,nama_konsumen,salesman,wilayah,area,clrdet,no_ka_id,floc,acc,catatan;
    String tglpicking, numberidpicking, ratepicking;

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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        txtdetrowPick = findViewById(R.id.txtdetrowPick);
        detpicktgl = findViewById(R.id.detpicktgl);
        detpickrate = findViewById(R.id.detpickrate);
        detpicknama = findViewById(R.id.detpicknama);
        detpicksales = findViewById(R.id.detpicksales);
        detpicknumberid = findViewById(R.id.detpicknumberid);
        detpicknokaid = findViewById(R.id.detpicknokaid);
        detpickclr = findViewById(R.id.detpickclr);
        detpickfloc = findViewById(R.id.detpickfloc);
        detpickacc = findViewById(R.id.detpickacc);
        detpickcatatan = findViewById(R.id.detpickcatatan);
        detpickwilayah = findViewById(R.id.detpickwilayah);

        Intent intent = getIntent();
        numberid = intent.getStringExtra("numberid");
        nokaid = intent.getStringExtra("nokaid");
        clr = intent.getStringExtra("clr");
        datarow = intent.getStringExtra("datarow");
        posisi = intent.getStringExtra("selected");
        tglpicking = intent.getStringExtra("tglpicking");
        numberidpicking = intent.getStringExtra("numberidpicking");
        ratepicking = intent.getStringExtra("rate");

        txtdetrowPick.setText("Item Picking "+posisi+" dari "+datarow);

        cekData(numberid);

        btnPicking = findViewById(R.id.btndetPick);

        btnPicking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimData(numberid);
                /*new AlertDialog.Builder(DetailPickingActivity.this)
                        .setTitle("Proses Picking")
                        .setMessage("Proses Konfirmasi Picking Sukses Dilakukan")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //kirimData(nomorid,userid);
                                //Log.e("lempar kirim ","coba error engga");
                            }}).show();*/
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void kirimData(final String nomor_id){
        // Tag used to cancel the request
        String tag_string_req = "req_request";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_SET_PICKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Picking Set Response: " + response.toString());
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    String error = jObj.getString("status");
                    Log.e(TAG, "obj: " + error);
                    // Check for error node in json
                    if (error.equals("1")) {
                        // user successfully logged in
                        // Create login session
                        // Launch main activity
                        new AlertDialog.Builder(DetailPickingActivity.this)
                                .setTitle("Proses Picking")
                                .setMessage("Proses Konfirmasi Picking Sukses Dilakukan")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Intent intent = new Intent(DetailPickingActivity.this,
                                                ProsesPickingActivity.class);
                                        intent.putExtra("tanggal",tgl);
                                        intent.putExtra("rate",ratepick);
                                        intent.putExtra("tglpicking",tglpicking);
                                        intent.putExtra("numberidpicking",numberidpicking);
                                        intent.putExtra("rate",ratepicking);
                                        startActivity(intent);
                                        finish();

                                    }}).show();

                    }else{
                        new AlertDialog.Builder(DetailPickingActivity.this)
                                .setTitle("Proses Picking")
                                .setMessage("Proses Konfirmasi Picking Gagal Dilakukan")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }}).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                /*Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();*/
                Log.e(TAG, "Picking Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Picking Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("numberid", numberid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void cekData(final String numberid){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DETAIL_PICKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Picking Detail Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray queArray = new JSONArray(response);
                    //now looping through all the elements of the json array
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        nomor_id = queObject.getString("nomor_id");
                        tgl = queObject.getString("tanggal");
                        ratepick = queObject.getString("rate");
                        nama_konsumen = queObject.getString("nama_konsumen");
                        salesman = queObject.getString("salesman");
                        wilayah = queObject.getString("wilayah");
                        area = queObject.getString("area");
                        clrdet = queObject.getString("clr");
                        no_ka_id = queObject.getString("no_ka_id");
                        floc = queObject.getString("floc");
                        acc = queObject.getString("acc");
                        catatan = queObject.getString("catatan");
                    }
                    detpicktgl.setText(tgl);
                    detpickrate.setText(ratepick);
                    detpicknama.setText(nama_konsumen);
                    detpickacc.setText(acc);
                    detpicknumberid.setText(nomor_id);
                    detpicksales.setText(salesman);
                    detpickcatatan.setText(catatan);
                    detpickclr.setText(clrdet);
                    detpickfloc.setText(floc);
                    detpicknokaid.setText(no_ka_id);
                    detpickwilayah.setText(wilayah);
                    //now looping through all the elements of the json array


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                /*Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();*/
                Log.e(TAG, "Picking Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Picking Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("numberid", numberid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
