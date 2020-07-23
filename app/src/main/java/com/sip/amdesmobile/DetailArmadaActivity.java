package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailArmadaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataDetailArmada> dataList = new ArrayList<>();
    private ListAdapter adapter;
    String rowdata, namauser, rowterpakai,tglroute,numberidroute,rateroute,idmobil,nopol,namadriver;
    private SessionManager session;
    AlertDialog dialog;
    private static final String TAG = DetailArmadaActivity.class.getSimpleName();
    Button returnRoute;
    TextView txtusername,txtrowdata,detidmobil,detnopol,detnamadriver,detarmadatgl,detarmadarate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_armada);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetarmada);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Report Armada");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

        txtusername = findViewById(R.id.txtusernamedetarmada);
        txtrowdata = findViewById(R.id.detrowdata);
        returnRoute = findViewById(R.id.returnDetarmada);
        detidmobil = findViewById(R.id.detidmobil);
        detnopol = findViewById(R.id.detnopol);
        detnamadriver = findViewById(R.id.detnamadriver);
        detarmadatgl = findViewById(R.id.detarmadatgl);
        detarmadarate = findViewById(R.id.detarmadarate);

        Intent intent = getIntent();
        tglroute= intent.getStringExtra("tglroute");
        numberidroute = intent.getStringExtra("numberidroute");
        rateroute = intent.getStringExtra("rate");
        rowdata = intent.getStringExtra("datarow");
        idmobil = intent.getStringExtra("idmobil");
        nopol = intent.getStringExtra("nopol");
        namadriver = intent.getStringExtra("driver");

        txtusername.setText(namauser);
        detidmobil.setText(idmobil);
        detnopol.setText(nopol);
        detnamadriver.setText(namadriver);
        detarmadatgl.setText(tglroute);
        detarmadarate.setText(rateroute);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        returnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( DetailArmadaActivity.this, ProsesRouteActivity.class);
                intent.putExtra("tglroute",tglroute);
                intent.putExtra("numberidroute",numberidroute);
                intent.putExtra("rate",rateroute);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.detarmadarecycler);
        adapter = new ListAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cekData(idmobil);

    }

    private void cekData(final String idmobil){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DETAIL_ARMADA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Armada Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    rowdata = jObj.getString("rowdata");
                    ArrayList data = new ArrayList<DataDetailArmada>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataDetailArmada(
                                        queObject.getString("numberid"),
                                        queObject.getString("area")
                                )
                        );
                        //getting the json object of the particular index inside the array
                        Log.e("debug looping",queObject.getString("numberid"));
                    }
                    adapter = new ListAdapter(data);
                    recyclerView.setAdapter(adapter);
                    txtrowdata.setText(rowdata+"/4");
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Armada Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Armada Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("idmobil", idmobil);

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

    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataDetailArmada> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView idnumber, kota;
            public CardView cardList;
            public Button cancelData;

            public MyViewHolder(View view) {
                super(view);
                idnumber = (TextView) view.findViewById(R.id.txtnumberid);
                kota = (TextView) view.findViewById(R.id.txtdetkota);
                cancelData = (Button) view.findViewById(R.id.btndetCancel);
                cardList = (CardView) view.findViewById(R.id.cardDetailArmada);
            }
        }

        public ListAdapter(List<DataDetailArmada> taskList) {
            this.taskList = taskList;
        }

        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_detailarmada, parent, false);

            return new ListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListAdapter.MyViewHolder holder, final int position) {
            final DataDetailArmada task = taskList.get(position);
            holder.idnumber.setText(task.getIdnumber());
            holder.kota.setText(task.getKota());

            holder.cancelData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    new AlertDialog.Builder(DetailArmadaActivity.this)
                            .setTitle("Hapus Routing")
                            .setMessage("Apakah anda yakin ingin menghapus data routing")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    cancelData(task.getIdnumber());
                                }}).setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

    }

    private void cancelData(final String idnumber){
        // Tag used to cancel the request
        String tag_string_req = "req_request";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_CANCEL_ROUTE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Cancel Armada Response: " + response.toString());
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    String error = jObj.getString("status");
                    Log.e(TAG, "obj: " + error);
                    // Check for error node in json
                    if (error.equals("1")) {
                        Toast.makeText(getApplicationContext(), "Hapus Data Success !", Toast.LENGTH_LONG).show();
                        cekData(idmobil);
                    }else{
                        Toast.makeText(getApplicationContext(), "Hapus Data Gagal !", Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Cancel Armada Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Cancel Armada Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("numberid", idnumber);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
