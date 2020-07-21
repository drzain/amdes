package com.sip.amdesmobile;

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

public class ProsesRouteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataRoute> dataList = new ArrayList<>();
    private ListAdapter adapter;
    String tglroute, numberidroute, rateroute,rowdata;
    AlertDialog dialog;
    private static final String TAG = ProsesPickingActivity.class.getSimpleName();
    TextView txtrowroute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprosesroute);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ROUTE");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        txtrowroute = findViewById(R.id.txtrowdataprosesroute);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        Intent intent = getIntent();
        tglroute= intent.getStringExtra("tglroute");
        numberidroute = intent.getStringExtra("numberidroute");
        rateroute = intent.getStringExtra("rate");

        //addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerRoute);
        adapter = new ListAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if (numberidroute != null && !numberidroute.isEmpty() && !numberidroute.equals("null")){
            cekdata(tglroute,numberidroute,rateroute);
        }else{
            Log.e("string proses",tglroute+" "+rateroute);
            cekdata2(tglroute,rateroute);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void cekdata(final String tglroute,final String numberidroute,final String rateroute){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_ROUTE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Routing Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    rowdata = jObj.getString("rowdata");
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    ArrayList data = new ArrayList<DataRoute>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataRoute(
                                        queObject.getString("nomor_id"),
                                        queObject.getString("nama_konsumen"),
                                        queObject.getString("rate"),
                                        queObject.getString("tanggal"),
                                        queObject.getString("salesman"),
                                        queObject.getString("wilayah"),
                                        queObject.getString("area"),
                                        queObject.getString("clr"),
                                        queObject.getString("no_ka_id")
                                )
                        );
                        //getting the json object of the particular index inside the array

                    }
                    adapter = new ListAdapter(data);
                    recyclerView.setAdapter(adapter);
                    txtrowroute.setText("Total Routing : "+ rowdata);
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
                params.put("tanggal", tglroute);
                params.put("numberid", numberidroute);
                params.put("rate", rateroute);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void cekdata2(final String tglroute,final String rateroute){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_ROUTE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Routing Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    rowdata = jObj.getString("rowdata");
                    txtrowroute.setText("Total Routing : "+ rowdata);
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    ArrayList data = new ArrayList<DataRoute>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataRoute(
                                        queObject.getString("nomor_id"),
                                        queObject.getString("nama_konsumen"),
                                        queObject.getString("rate"),
                                        queObject.getString("tanggal"),
                                        queObject.getString("salesman"),
                                        queObject.getString("wilayah"),
                                        queObject.getString("area"),
                                        queObject.getString("clr"),
                                        queObject.getString("no_ka_id")
                                )
                        );
                        //getting the json object of the particular index inside the array

                    }
                    adapter = new ListAdapter(data);
                    recyclerView.setAdapter(adapter);

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
                params.put("tanggal", tglroute);
                params.put("rate", rateroute);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /*void addData(){
        routeArrayList = new ArrayList<DataRoute>();
        routeArrayList.add(new DataRoute("72674526", "Ajeng","2", "03/07/2020","Tri Sutisna","Setu","Bekasi"));
        routeArrayList.add(new DataRoute("63764827", "Ade Irwana","2", "03/07/2020","Tri Sutisna","Sentul","Bogor"));
        routeArrayList.add(new DataRoute("83748362", "Sunan Ali", "2", "03/07/2020","Tri Sutisna","Cililitan","Jakarta"));
    }*/

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataRoute> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView numberid, nama, rate, tanggal,salesman, wilayah, area;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                numberid = (TextView) view.findViewById(R.id.routeNomorId);
                area = (TextView) view.findViewById(R.id.routeArea);
                wilayah = (TextView) view.findViewById(R.id.routeWilayah);
                cardList = (CardView) view.findViewById(R.id.cardroute);
            }
        }

        public ListAdapter(List<DataRoute> taskList) {
            this.taskList = taskList;
        }

        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_route, parent, false);

            return new ListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListAdapter.MyViewHolder holder, final int position) {
            final DataRoute task = taskList.get(position);
            holder.numberid.setText(task.getNomorid());
            holder.area.setText(task.getArea());
            holder.wilayah.setText(task.getWilayah());

            holder.cardList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    /*new AlertDialog.Builder(view.getContext())
                            .setTitle("Proses Picking")
                            .setMessage("Apakah kamu yakin ingin memproses picking data ini?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String nomorid = taskList.get(position).getNomorid();
                                    Log.e("nomorid adapter",nomorid);
                                    //kirimData(nomorid,userid);
                                    //Log.e("lempar kirim ","coba error engga");
                                }})
                            .setNegativeButton(android.R.string.no, null).show();*/

                    Log.e("posisi ",String.valueOf(position));
                    Intent intent = new Intent( ProsesRouteActivity.this, FormRoutingActivity.class);
                    intent.putExtra("numberid",task.getNomorid());
                    intent.putExtra("wilayah",task.getWilayah());
                    intent.putExtra("area",task.getArea());
                    intent.putExtra("datarow",rowdata);
                    intent.putExtra("selected",String.valueOf(position+1));
                    intent.putExtra("tglpicking",tglroute);
                    intent.putExtra("numberidpicking",numberidroute);
                    intent.putExtra("rate",rateroute);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

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
