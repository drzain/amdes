package com.sip.amdesmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailLacakActivity extends AppCompatActivity {

    Button btn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lacak);

        btn_home = findViewById(R.id.btn_salesman_lacak_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailLacakActivity.this, SalesmanActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
