package com.sip.amdesmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PdiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataPdi> pdiArrayList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpdi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("PDI");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerPdi);
        adapter = new ListAdapter(pdiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PdiActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void addData(){
        pdiArrayList = new ArrayList<DataPdi>();
        pdiArrayList.add(new DataPdi("72674526", "Ajeng","2", "03/07/2020","Tri Sutisna","Setu","Bekasi"));
        pdiArrayList.add(new DataPdi("63764827", "Ade Irwana","2", "03/07/2020","Tri Sutisna","Sentul","Bogor"));
        pdiArrayList.add(new DataPdi("83748362", "Sunan Ali", "2", "03/07/2020","Tri Sutisna","Cililitan","Jakarta"));
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private List<DataPdi> taskList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView numberid, nama, rate, tanggal,salesman, wilayah, area;
            public CardView cardList;

            public MyViewHolder(View view) {
                super(view);
                numberid = (TextView) view.findViewById(R.id.pdiNomorId);
                nama = (TextView) view.findViewById(R.id.pdiNama);
                rate = (TextView) view.findViewById(R.id.pdiRate);
                tanggal = (TextView) view.findViewById(R.id.pdiTanggal);
                salesman = (TextView) view.findViewById(R.id.pdiSales);
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

                    Intent intent = new Intent( PdiActivity.this, FormPdiActivity.class);
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