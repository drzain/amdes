package com.sip.amdesmobile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername,edtPassword;
    String username, password;
    private SessionManager session;
    AlertDialog dialog;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonSignin);
        session = new SessionManager(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString();
                password = edtPassword.getText().toString();
                checkLogin(username,password);
                /*if(edtUsername.getText().toString().equals("picking")){
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
                }*/

            }
        });

        cekpermission();

    }

    public void cekpermission(){

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {


                } else if (report.isAnyPermissionPermanentlyDenied()) {
                    showPermissionsAlert();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

    }

    /**
     * Alert dialog to navigate to app settings
     * to enable necessary permissions
     */
    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(MainActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN, new Response.Listener<String>() {

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
                        session.setLogin(true);

                        String userid = jObj.getString("userid");
                        String foto = jObj.getString("foto");
                        String email = jObj.getString("email");
                        String phone = jObj.getString("phone");
                        //String token = jObj.getString("token");
                        String user = jObj.getString("username");
                        String role = jObj.getString("role");
                        session.setIdUser(userid);
                        session.setNama(user);
                        session.setFoto(foto);
                        session.setEmail(email);
                        session.setPhone(phone);
                        //session.setToken(token);

                        if(role.equals("8")){
                            Intent intent = new Intent(MainActivity.this, PickingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(role.equals("9")){
                            Intent intent = new Intent(MainActivity.this, PdiActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(role.equals("10")){
                            Intent intent = new Intent(MainActivity.this, RouteActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(role.equals("11")){
                            Intent intent = new Intent(MainActivity.this, ShippingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(role.equals("12")){
                            Intent intent = new Intent(MainActivity.this, SalesmanActivity.class);
                            startActivity(intent);
                            finish();
                        }


                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Akses user belum dibuka", Toast.LENGTH_LONG).show();
                            session.editor.clear().commit();
                        }
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Login Failed", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

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
}
