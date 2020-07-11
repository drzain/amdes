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

public class ProsesPickingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataPicking> dataList = new ArrayList<>();
    private ListAdapter adapter;
    String tglpicking, numberidpicking, ratepicking,rowdata;
    AlertDialog dialog;
    private static final String TAG = ProsesPickingActivity.class.getSimpleName();
    TextView txtrowpick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_picking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprosespicking);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Picking");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        txtrowpick = findViewById(R.id.txtrowpick);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        Intent intent = getIntent();
        tglpicking = intent.getStringExtra("tglpicking");
        numberidpicking = intent.getStringExtra("numberidpicking");
        ratepicking = intent.getStringExtra("rate");

        //addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerPicking);
        adapter = new ListAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if (numberidpicking != null && !numberidpicking.isEmpty() && !numberidpicking.equals("null")){
            //cekdata(tglpicking,numberidpicking,ratepicking);
        }else{
            Log.e("string proses",tglpicking+" "+ratepicking);
            cekdata2(tglpicking,ratepicking);
        }
    }

    /*void addData(){
        pickingArrayList = new ArrayList<DataPicking>();
        pickingArrayList.add(new DataPicking("72674526", "Ajeng","2", "07/07/2020","Tri Sutisna","Setu","Bekasi","CLR","72627627"));
        pickingArrayList.add(new DataPicking("63764827", "Ade Irwana","2", "07/07/2020","Tri Sutisna","Sentul","Bogor","CLR","2352352"));
        pickingArrayList.add(new DataPicking("83748362", "Sunan Ali", "2", "07/07/2020","Tri Sutisna","Cililitan","Jakarta","CLR","32526343"));
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void cekdata(final String tglpicking,final String numberidpicking, final String ratepicking){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_PICKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Picking Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    rowdata = jObj.getString("rowdata");
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    ArrayList data = new ArrayList<DataPicking>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataPicking(
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
                    txtrowpick.setText("Total Picking : "+ rowdata);
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
                params.put("tanggal", tglpicking);
                params.put("numberid", numberidpicking);
                params.put("rate", ratepicking);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void cekdata2(final String tglpicking, final String ratepicking){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_PICKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Picking Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    rowdata = jObj.getString("rowdata");
                    txtrowpick.setText("Total Picking : "+ rowdata);
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    ArrayList data = new ArrayList<DataPicking>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataPicking(
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
                params.put("tanggal", tglpicking);
                params.put("rate", ratepicking);

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

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataPicking> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView numberid, noka, clr;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                numberid = (TextView) view.findViewById(R.id.pickNomorId);
                noka = (TextView) view.findViewById(R.id.pickNoKa);
                clr = (TextView) view.findViewById(R.id.pickCLR);
                cardList = (CardView) view.findViewById(R.id.cardpicking);
            }
        }

        public ListAdapter(List<DataPicking> taskList) {
            this.taskList = taskList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_picking, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final DataPicking task = taskList.get(position);
            holder.numberid.setText(task.getNomorid());
            holder.noka.setText(task.getNokaid());
            holder.clr.setText(task.getClr());

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
                    Intent intent = new Intent( ProsesPickingActivity.this, DetailPickingActivity.class);
                    intent.putExtra("numberid",task.getNomorid());
                    intent.putExtra("nokaid",task.getNokaid());
                    intent.putExtra("clr",task.getClr());
                    intent.putExtra("datarow",rowdata);
                    intent.putExtra("selected",String.valueOf(position+1));
                    intent.putExtra("tglpicking",tglpicking);
                    intent.putExtra("numberidpicking",numberidpicking);
                    intent.putExtra("rate",ratepicking);
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
