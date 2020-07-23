package com.sip.amdesmobile;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class ReportArmadaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataArmada> dataList = new ArrayList<>();
    private ListAdapter adapter;
    String rowdata, namauser, rowterpakai,tglroute,numberidroute,rateroute;
    private SessionManager session;
    AlertDialog dialog;
    private static final String TAG = ReportArmadaActivity.class.getSimpleName();
    TextView usernamearmada,jumlahmobil,jumlahterpakai;
    Button returnRoute;
    FloatingActionButton btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_armada);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbararmada);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbararmada_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("Report Armada");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        usernamearmada = findViewById(R.id.usernamearmada);
        jumlahmobil = findViewById(R.id.jumlahmobil);
        jumlahterpakai = findViewById(R.id.jumlahterpakai);

        Intent intent = getIntent();
        tglroute= intent.getStringExtra("tglroute");
        numberidroute = intent.getStringExtra("numberidroute");
        rateroute = intent.getStringExtra("rate");

        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

        usernamearmada.setText(namauser);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        recyclerView = (RecyclerView) findViewById(R.id.armadarecycler);
        adapter = new ListAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cekData();

        returnRoute = findViewById(R.id.returnRoute);
        returnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ReportArmadaActivity.this, ProsesRouteActivity.class);
                intent.putExtra("tglroute",tglroute);
                intent.putExtra("numberidroute",numberidroute);
                intent.putExtra("rate",rateroute);
                startActivity(intent);
                finish();
            }
        });

        btnKeluar = findViewById(R.id.btn_keluar_armada);
        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                Intent intent = new Intent( ReportArmadaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cekData(){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_ARMADA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "armada Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    rowdata = jObj.getString("rowdata");
                    rowterpakai = jObj.getString("terpakai");
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    ArrayList data = new ArrayList<DataArmada>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataArmada(
                                        queObject.getString("idmobil"),
                                        queObject.getString("nopol"),
                                        queObject.getString("firstname"),
                                        queObject.getString("kapasitas")
                                )
                        );
                        //getting the json object of the particular index inside the array

                    }
                    adapter = new ListAdapter(data);
                    recyclerView.setAdapter(adapter);
                    jumlahmobil.setText(rowdata);
                    jumlahterpakai.setText(rowterpakai+"/"+(Integer.valueOf(rowdata)*4));
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
                Log.e(TAG, "Armada Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Armada Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataArmada> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView idmobil, nopol, driver, terpakai;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                idmobil = (TextView) view.findViewById(R.id.txtidmobil);
                nopol = (TextView) view.findViewById(R.id.txtnopol);
                driver = (TextView) view.findViewById(R.id.txtdriver);
                terpakai = (TextView) view.findViewById(R.id.txtterpakai);
                cardList = (CardView) view.findViewById(R.id.cardarmada);
            }
        }

        public ListAdapter(List<DataArmada> taskList) {
            this.taskList = taskList;
        }

        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_armada, parent, false);

            return new ListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListAdapter.MyViewHolder holder, final int position) {
            final DataArmada task = taskList.get(position);
            holder.idmobil.setText(task.getIdmobil());
            holder.nopol.setText(task.getNopol());
            holder.driver.setText(task.getNamadriver());
            holder.terpakai.setText(task.getTerpakai()+"/4");

            holder.cardList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Intent intent = new Intent( ReportArmadaActivity.this, DetailArmadaActivity.class);
                    intent.putExtra("datarow",rowdata);
                    intent.putExtra("selected",String.valueOf(position+1));
                    intent.putExtra("tglroute",tglroute);
                    intent.putExtra("numberidroute",numberidroute);
                    intent.putExtra("rate",rateroute);
                    intent.putExtra("idmobil",task.getIdmobil());
                    intent.putExtra("nopol",task.getNopol());
                    intent.putExtra("driver",task.getNamadriver());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

    }
}
