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
import android.widget.ListAdapter;
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

public class PdiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataPdi> dataList = new ArrayList<>();
    private ListAdapter adapter;
    String rowdata, namauser;
    private SessionManager session;
    AlertDialog dialog;
    private static final String TAG = PdiActivity.class.getSimpleName();
    FloatingActionButton btnKeluar;
    TextView txtrowpdi,txtusernamepdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpdi);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarpdi_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("PDI");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        session = new SessionManager(getApplicationContext());
        namauser = session.isNama();

        btnKeluar = findViewById(R.id.btn_keluar_pdi);
        txtrowpdi = findViewById(R.id.txtrowpdi);
        txtusernamepdi = findViewById(R.id.usernamePdi);

        txtusernamepdi.setText(namauser);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPdi);
        adapter = new ListAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cekdata();

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                Intent intent = new Intent( PdiActivity.this, MainActivity.class);
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

    private void cekdata(){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_PDI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Pdi Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e(TAG, "obj: " + jObj.toString());
                    rowdata = jObj.getString("rowdata");
                    JSONArray queArray = jObj.getJSONArray("data");
                    //now looping through all the elements of the json array
                    ArrayList data = new ArrayList<DataPdi>();
                    for (int i = 0; i < queArray.length(); i++) {
                        JSONObject queObject = queArray.getJSONObject(i);
                        data.add(
                                new DataPdi(
                                        queObject.getString("nomor_id"),
                                        queObject.getString("nama_konsumen"),
                                        queObject.getString("rate"),
                                        queObject.getString("tanggal"),
                                        queObject.getString("salesman"),
                                        queObject.getString("wilayah"),
                                        queObject.getString("area"),
                                        queObject.getString("no_ka_id"),
                                        queObject.getString("acc")
                                )
                        );
                        //getting the json object of the particular index inside the array

                    }
                    adapter = new PdiActivity.ListAdapter(data);
                    recyclerView.setAdapter(adapter);
                    txtrowpdi.setText("Total PDI : "+ rowdata);
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
                Log.e(TAG, "Pdi Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Pdi Data Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

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

    /*void addData(){
        pdiArrayList = new ArrayList<DataPdi>();
        pdiArrayList.add(new DataPdi("72674526", "Ajeng","2", "03/07/2020","Tri Sutisna","Setu","Bekasi","8297423","Acc"));
        pdiArrayList.add(new DataPdi("63764827", "Ade Irwana","2", "03/07/2020","Tri Sutisna","Sentul","Bogor","6382742","Acc"));
        pdiArrayList.add(new DataPdi("83748362", "Sunan Ali", "2", "03/07/2020","Tri Sutisna","Cililitan","Jakarta","57264244","Acc"));
    }*/

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataPdi> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView numberid, nokaid, acc;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                numberid = (TextView) view.findViewById(R.id.pdiNomorId);
                nokaid = (TextView) view.findViewById(R.id.pdiNoKaid);
                acc = (TextView) view.findViewById(R.id.pdiACC);
                cardList = (CardView) view.findViewById(R.id.cardpdi);
            }
        }

        public ListAdapter(List<DataPdi> taskList) {
            this.taskList = taskList;
        }

        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_pdi, parent, false);

            return new ListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListAdapter.MyViewHolder holder, final int position) {
            final DataPdi task = taskList.get(position);
            holder.numberid.setText(task.getNomorid());
            holder.nokaid.setText(task.getNokaid());
            holder.acc.setText(task.getAcc());

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

                    Intent intent = new Intent( PdiActivity.this, FormPdiActivity.class);
                    intent.putExtra("numberid",task.getNomorid());
                    intent.putExtra("nokaid",task.getNokaid());
                    intent.putExtra("acc",task.getAcc());
                    intent.putExtra("datarow",rowdata);
                    intent.putExtra("selected",String.valueOf(position+1));
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
