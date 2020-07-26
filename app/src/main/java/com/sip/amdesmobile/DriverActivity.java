package com.sip.amdesmobile;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    public BackgroundService gpsService;
    public boolean mTracking = false;
    private RecyclerView recyclerView;
    private ArrayList<DataTask> driverArrayList;
    private ListAdapter adapter;
    private SessionManager session;
    Button btnStartTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardriver);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("DRIVER");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerDriver);
        adapter = new ListAdapter(driverArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DriverActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        btnStartTracking = findViewById(R.id.btn_start_tracking);

        final Intent intent = new Intent(this.getApplication(), BackgroundService.class);
        this.getApplication().startService(intent);
//        this.getApplication().startForegroundService(intent);
        this.getApplication().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnStartTracking.performClick();
            }
        }, 1000);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("BackgroundService")) {
                gpsService = ((BackgroundService.LocationServiceBinder) service).getService();
                btnStartTracking.setEnabled(true);
                //txtStatus.setText("GPS Ready");
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("BackgroundService")) {
                gpsService = null;
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void addData(){
        driverArrayList = new ArrayList<DataTask>();
        driverArrayList.add(new DataTask("72674526", "Ajeng","2", "03/07/2020"));
        driverArrayList.add(new DataTask("63764827", "Ade Irwana","2", "03/07/2020"));
        driverArrayList.add(new DataTask("83748362", "Sunan Ali", "2", "03/07/2020"));
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataTask> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView numberid, nama, rate, tanggal,salesman, wilayah, area;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                numberid = (TextView) view.findViewById(R.id.listNumberId);
                nama = (TextView) view.findViewById(R.id.listNama);
                rate = (TextView) view.findViewById(R.id.listRate);
                tanggal = (TextView) view.findViewById(R.id.listTanggal);
                cardList = (CardView) view.findViewById(R.id.cardpilihan);
            }
        }

        public ListAdapter(List<DataTask> taskList) {
            this.taskList = taskList;
        }

        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_dashboard, parent, false);

            return new ListAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListAdapter.MyViewHolder holder, final int position) {
            final DataTask task = taskList.get(position);
            holder.numberid.setText(task.getNumberid());
            holder.nama.setText(task.getNamakonsumen());
            holder.rate.setText(task.getRate());
            holder.tanggal.setText(task.getTanggal());

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

                    if(task.getNumberid().equals("63764827")){
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Proses Pengiriman")
                                .setMessage("Apakah kendaraan diterima oleh konsumen?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent intent = new Intent( DriverActivity.this, FormTerkirimActivity.class);
                                        startActivity(intent);
                                    }})
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent intent = new Intent( DriverActivity.this, GagalKirimActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                    }else{
                        Intent intent = new Intent( DriverActivity.this, FormDriverActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

    }

}
