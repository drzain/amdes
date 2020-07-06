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

import java.util.ArrayList;
import java.util.List;

public class ProsesPickingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataPicking> pickingArrayList;
    private ListAdapter adapter;

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

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerPicking);
        adapter = new ListAdapter(pickingArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProsesPickingActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    void addData(){
        pickingArrayList = new ArrayList<DataPicking>();
        pickingArrayList.add(new DataPicking("03/07/2020", "Ajeng","2", "72674526","Tri Sutisna","Setu","Bekasi"));
        pickingArrayList.add(new DataPicking("03/07/2020", "Ade Irwana","2", "63764827","Tri Sutisna","Sentul","Bogor"));
        pickingArrayList.add(new DataPicking("03/07/2020", "Sunan Ali", "2", "83748362","Tri Sutisna","Cililitan","Jakarta"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataPicking> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView numberid, nama, rate, tanggal,salesman, wilayah, area;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                numberid = (TextView) view.findViewById(R.id.pickNomorId);
                nama = (TextView) view.findViewById(R.id.pickNama);
                rate = (TextView) view.findViewById(R.id.pickRate);
                tanggal = (TextView) view.findViewById(R.id.pickTanggal);
                salesman = (TextView) view.findViewById(R.id.pickSales);
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
            holder.nama.setText(task.getNamakonsumen());
            holder.rate.setText(task.getRate());
            holder.tanggal.setText(task.getTanggal());
            holder.salesman.setText(task.getSalesman());

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
