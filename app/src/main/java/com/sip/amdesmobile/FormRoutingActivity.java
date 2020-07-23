package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

public class FormRoutingActivity extends AppCompatActivity {

    Button btnConfirmRoute;
    TextView txtformuserroute;
    String namauser, mobilkirim;
    private SessionManager session;
    Spinner spinmobil;
    String numberid,bnumberid,bnokaid,bacc,datarow,posisi,tglroute,numberidroute,rateroute;
    AlertDialog dialog;
    private static final String TAG = FormPdiActivity.class.getSimpleName();
    SpinnerAdapter adapter;
    List<DataMobil> listMobil = new ArrayList<DataMobil>();
    String nomor_id,tgl,ratepdi,nama_konsumen,salesman,wilayah,area,clrdet,no_ka_id,floc,acc,catatan;
    TextView routeformsalesman,routeformwilayah,routeformcatatan,routeformacc,routeformclr,routeformnokaid,routeformnomorid,routeformnama,routeformrate,routeformtgl,routeformrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_routing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarformrouting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ROUTE");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        btnConfirmRoute = findViewById(R.id.btnConfirmRoute);
        txtformuserroute = findViewById(R.id.formroutingusername);
        routeformsalesman = findViewById(R.id.routeformsales);
        routeformwilayah = findViewById(R.id.routeformwilayah);
        routeformacc = findViewById(R.id.routeformacc);
        routeformnokaid = findViewById(R.id.routeformnokaid);
        routeformnomorid = findViewById(R.id.routeformnomorid);
        routeformnama = findViewById(R.id.routeformnama);
        routeformrate = findViewById(R.id.routeformrate);
        routeformtgl = findViewById(R.id.routeformtgl);
        routeformrow = findViewById(R.id.routeformrow);
        spinmobil = findViewById(R.id.routespinmobil);

        Intent intent = getIntent();
        bnumberid = intent.getStringExtra("numberid");
        datarow = intent.getStringExtra("datarow");
        posisi = intent.getStringExtra("selected");
        tglroute= intent.getStringExtra("tglroute");
        numberidroute = intent.getStringExtra("numberidroute");
        rateroute = intent.getStringExtra("rate");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        routeformrow.setText("Item Routing "+posisi+" dari "+datarow);

        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

        txtformuserroute.setText(namauser);

        adapter = new SpinnerAdapter(FormRoutingActivity.this, listMobil);
        spinmobil.setAdapter(adapter);

        spinmobil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                mobilkirim = listMobil.get(position).getIdmobil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        loadMobil();

        cekData(bnumberid);

        btnConfirmRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new AlertDialog.Builder(FormRoutingActivity.this)
                        .setTitle("Proses Route")
                        .setMessage("Proses Konfirmasi Route Sukses Dilakukan")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //kirimData(nomorid,userid);
                                //Log.e("lempar kirim ","coba error engga");
                            }}).show();*/
                kirimData(bnumberid,mobilkirim,namauser);
            }
        });
    }

    private void kirimData(final String bnumberid,final String mobilkirim,final String namauser){
        // Tag used to cancel the request
        String tag_string_req = "req_request";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_SET_ROUTE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Routing Set Response: " + response.toString());
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
                        new AlertDialog.Builder(FormRoutingActivity.this)
                                .setTitle("Proses Routing")
                                .setMessage("Proses Konfirmasi Routing Sukses Dilakukan")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        Intent intent = new Intent(FormRoutingActivity.this,
                                                ReportArmadaActivity.class);
                                        intent.putExtra("tglroute",tglroute);
                                        intent.putExtra("numberidroute",numberidroute);
                                        intent.putExtra("rate",rateroute);
                                        startActivity(intent);
                                        finish();

                                    }}).show();

                    }else{
                        new AlertDialog.Builder(FormRoutingActivity.this)
                                .setTitle("Proses Routing")
                                .setMessage("Proses Konfirmasi Routing Gagal Dilakukan, Cek Koneksi Internet Anda")
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
                Log.e(TAG, "Routing Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Routing Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("numberid", bnumberid);
                params.put("idmobil", mobilkirim);
                params.put("username", namauser);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
                        no_ka_id = queObject.getString("no_ka_id");
                        acc = queObject.getString("acc");
                    }
                    routeformwilayah.setText(wilayah);
                    routeformsalesman.setText(salesman);
                    routeformnokaid.setText(no_ka_id);
                    routeformacc.setText(acc);
                    routeformnomorid.setText(nomor_id);
                    routeformnama.setText(nama_konsumen);
                    routeformrate.setText(ratepdi);
                    routeformtgl.setText(tgl);

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

    public void loadMobil(){
        String tag_string_req = "req_task";
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_MOBIL_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("respon",response);
                            //getting the whole json object from the response
                            //JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray queArray = new JSONArray(response);
                            //now looping through all the elements of the json array
                            //ArrayList data = new ArrayList<DataMobil>();
                            for (int i = 0; i < queArray.length(); i++) {
                                JSONObject queObject = queArray.getJSONObject(i);
                                DataMobil item = new DataMobil();

                                item.setIdmobil(queObject.getString("idmobil"));
                                item.setPlatmobil(queObject.getString("nopol"));

                                listMobil.add(item);
                                //getting the json object of the particular index inside the array

                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        try {
                            Toast.makeText(FormRoutingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception x){
                            x.printStackTrace();
                        }
                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
