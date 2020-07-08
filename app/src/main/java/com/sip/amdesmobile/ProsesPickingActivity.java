package com.sip.amdesmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProsesPickingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataPicking> pickingArrayList;
    private ListAdapter adapter;
    String tglpicking, numberidpicking, ratepicking;
    AlertDialog dialog;
    private static final String TAG = MainActivity.class.getSimpleName();

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

        Intent intent = getIntent();
        tglpicking = intent.getStringExtra("tglpicking");
        numberidpicking = intent.getStringExtra("numberidpicking");
        ratepicking = intent.getStringExtra("rate");

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerPicking);
        adapter = new ListAdapter(pickingArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProsesPickingActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    void addData(){
        pickingArrayList = new ArrayList<DataPicking>();
        pickingArrayList.add(new DataPicking("03/07/2020", "Ajeng","2", "72674526","Tri Sutisna","Setu","Bekasi","CLR","72627627"));
        pickingArrayList.add(new DataPicking("03/07/2020", "Ade Irwana","2", "63764827","Tri Sutisna","Sentul","Bogor","CLR","2352352"));
        pickingArrayList.add(new DataPicking("03/07/2020", "Sunan Ali", "2", "83748362","Tri Sutisna","Cililitan","Jakarta","CLR","32526343"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void cekdata(){
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_DATA_PICKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
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

                        //Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        //startActivity(intent);
                        //finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
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
                //params.put("username", username);
                //params.put("password", password);

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

                    Intent intent = new Intent( ProsesPickingActivity.this, DetailPickingActivity.class);
                    intent.putExtra("numberid",task.getNomorid());
                    intent.putExtra("nokaid",task.getNokaid());
                    intent.putExtra("clr",task.getClr());
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
