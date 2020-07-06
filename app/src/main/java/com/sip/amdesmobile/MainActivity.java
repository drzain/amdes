package com.sip.amdesmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonSignin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsername.getText().toString().equals("picking")){
                    Intent intent = new Intent( MainActivity.this, PickingActivity.class);
                    startActivity(intent);
                }else if(edtUsername.getText().toString().equals("pdi")){
                    Intent intent = new Intent( MainActivity.this, PdiActivity.class);
                    startActivity(intent);
                }else if(edtUsername.getText().toString().equals("route")){
                    Intent intent = new Intent( MainActivity.this, RouteActivity.class);
                    startActivity(intent);
                }else if(edtUsername.getText().toString().equals("driver")){
                    Intent intent = new Intent( MainActivity.this, DriverActivity.class);
                    startActivity(intent);
                }else if(edtUsername.getText().toString().equals("sales")){
                    Intent intent = new Intent( MainActivity.this, SalesmanActivity.class);
                    startActivity(intent);
                }

            }
        });



    }
}
