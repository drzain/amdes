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
import android.widget.EditText;
import android.widget.Spinner;
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

public class FormPdiActivity extends AppCompatActivity {

    Button btnConfirmPdi;
    TextView txtdetuserpdi;
    String namauser;
    private SessionManager session;
    Spinner pdiaccesories;
    EditText formFisik,formMesin,formKm;
    AlertDialog dialog;
    String fisik,mesin,km,aksesoris,numberid,bnumberid,bnokaid,bacc,datarow,posisi;
    private static final String TAG = FormPdiActivity.class.getSimpleName();
    String nomor_id,tgl,ratepdi,nama_konsumen,salesman,wilayah,area,clrdet,no_ka_id,floc,acc,catatan;
    TextView pdiformarea,pdiformwilayah,pdiformsales,pdiformnomorid,pdiformnama,pdiformrate,pdiformtgl,pdiformrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pdi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarformpdi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("PDI");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnConfirmPdi = findViewById(R.id.btnConfirmPdi);
        txtdetuserpdi = findViewById(R.id.detusernamepdi);
        formFisik = findViewById(R.id.pdiformfisik);
        formMesin = findViewById(R.id.pdiformmesin);
        formKm = findViewById(R.id.pdiformkilometer);
        pdiaccesories = findViewById(R.id.pdiformaksesoris);
        pdiformarea = findViewById(R.id.pdiformarea);
        pdiformwilayah = findViewById(R.id.pdiformwilayah);
        pdiformsales = findViewById(R.id.pdiformsales);
        pdiformnomorid = findViewById(R.id.pdiformnomorid);
        pdiformnama = findViewById(R.id.pdiformnama);
        pdiformrate = findViewById(R.id.pdiformrate);
        pdiformtgl = findViewById(R.id.pdiformtgl);
        pdiformrow = findViewById(R.id.pdiformrow);

        Intent intent = getIntent();
        bnumberid = intent.getStringExtra("numberid");
        bnokaid = intent.getStringExtra("nokaid");
        bacc = intent.getStringExtra("acc");
        datarow = intent.getStringExtra("datarow");
        posisi = intent.getStringExtra("selected");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        pdiformrow.setText("Item Picking "+posisi+" dari "+datarow);

        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

        txtdetuserpdi.setText(namauser);

        cekData(bnumberid);

        btnConfirmPdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fisik = formFisik.getText().toString();
                mesin = formMesin.getText().toString();
                km = formKm.getText().toString();
                aksesoris = pdiaccesories.getSelectedItem().toString();
                kirimData(fisik,mesin,km,aksesoris,namauser,bnumberid);
            }
        });
    }

    private void cekData(final String bnumberid){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DETAIL_PDI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Pdi Response: " + response.toString());
                hideDialog();

                try {
                    JSONArray queArray = new JSONArray(response);
                    //now looping through all the elements of the json array
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        nomor_id = queObject.getString("nomor_id");
                        tgl = queObject.getString("tanggal");
                        ratepdi = queObject.getString("rate");
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
                    pdiformarea.setText(area);
                    pdiformwilayah.setText(wilayah);
                    pdiformsales.setText(salesman);
                    pdiformnomorid.setText(nomor_id);
                    pdiformnama.setText(nama_konsumen);
                    pdiformrate.setText(ratepdi);
                    pdiformtgl.setText(tgl);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Pdi Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "PDI Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("numberid", bnumberid);

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

    private void kirimData(final String fisik,final String mesin, final String km, final String aksesoris, final String namauser, final String bnumberid){
        // Tag used to cancel the request
        String tag_string_req = "req_request";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_SET_PDI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "PDI Set Response: " + response.toString());
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
                        new AlertDialog.Builder(FormPdiActivity.this)
                                .setTitle("Proses PDI")
                                .setMessage("Proses Konfirmasi PDI Sukses Dilakukan")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Intent intent = new Intent(FormPdiActivity.this,
                                                PdiActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }}).show();

                    }else{
                        new AlertDialog.Builder(FormPdiActivity.this)
                                .setTitle("Proses Picking")
                                .setMessage("Proses Konfirmasi Picking Gagal Dilakukan, Cek Koneksi Internet Anda")
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
                Log.e(TAG, "PDI Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "PDI Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("numberid", bnumberid);
                params.put("fisik", fisik);
                params.put("mesin", mesin);
                params.put("km", km);
                params.put("aksesoris", aksesoris);
                params.put("username", namauser);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
