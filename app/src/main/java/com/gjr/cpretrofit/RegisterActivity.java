package com.gjr.cpretrofit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gjr.cpretrofit.api.RetrofitClient;
import com.gjr.cpretrofit.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView textView_login, textView_all_user;
    EditText s_users_name, s_users_email, s_users_mobile, s_users_password;
    String users_name, users_email, users_mobile, users_password;
    public static String d_users_name, d_users_email, d_users_mobile, d_users_dor;
    Button button_register;
    static final String KEY_EMPTY_R = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ui_xmltojava_connect();
        validInputs_R();

        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        textView_all_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(RegisterActivity.this, GetUserActivityResponse.class));
                startActivity(new Intent(RegisterActivity.this, GetUserActivity.class));

            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                users_name = s_users_name.getText().toString().trim();
                users_email = s_users_email.getText().toString().trim();
                users_mobile = s_users_mobile.getText().toString().trim();
                users_password = s_users_password.getText().toString().trim();

                //NetworkCheck
                if (!isDeviceOnline_R()) {
                    //No Network
                    activate_online_device_R();
                } else {
                    //Network On
                    if (validInputs_R()) {
                        register_user();
                    }
                }

            }
        });
    }

    private void register_user() {
        displayloader();

        Call<User> userCall = RetrofitClient.getInstance().getMyApi().getUserRegister(users_name, users_email, users_mobile, users_password);
        System.out.println("Test PASS 0 " + users_name + " " + users_email + " " + users_mobile + "   " + users_password);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                System.out.println("Test PASS I " + response.toString().toUpperCase());
                assert response.body() != null;
                System.out.println("Test PASS II " + response.body().toString().toUpperCase());
                System.out.println("Test PASS III " + response.body().getMessage().toString().toUpperCase());

                if (user != null) {
                    if (user.getStatus().equals("true")) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                        //Data for Dashboard
                        d_users_name = users_name;
                        d_users_email = users_email;
                        d_users_mobile = users_mobile;
                        startActivity(new Intent(RegisterActivity.this, Dashboard.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Not Registered Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                System.out.println("Test F" + t.getMessage().toUpperCase());
            }
        });
    }

    private void displayloader() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("LOGGING IN.... Please Wait!");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void activate_online_device_R() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Network Error");
        builder.setMessage("No network connection available.");
        builder.setIcon(R.drawable.logo);
        builder.setPositiveButton("ACTIVATE INTERNET!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivityForResult(intent, 9003);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private boolean isDeviceOnline_R() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network networkInfo = connectivityManager.getActiveNetwork();
        return (networkInfo != null);
    }

    private boolean validInputs_R() {
        if (KEY_EMPTY_R.equals(users_name)) {
            s_users_name.setError("The username field is empty!");
            s_users_name.requestFocus();
            return false;
        }
        if (KEY_EMPTY_R.equals(users_email)) {
            s_users_email.setError("The email field is empty!");
            s_users_email.requestFocus();
            return false;
        }
        if (KEY_EMPTY_R.equals(users_mobile)) {
            s_users_mobile.setError("The mobile field is empty!");
            s_users_mobile.requestFocus();
            return false;
        }
        if (KEY_EMPTY_R.equals(users_password)) {
            s_users_password.setError("The password field is empty!");
            s_users_password.requestFocus();
            return false;
        }
        return true;
    }

    private void ui_xmltojava_connect() {
        textView_login = findViewById(R.id.textViewLogin);
        s_users_name = findViewById(R.id.editTextUsername);
        s_users_email = findViewById(R.id.editTextEmail);
        s_users_mobile = findViewById(R.id.editTextPhone);
        s_users_password = findViewById(R.id.editTextPassword);
        button_register = findViewById(R.id.buttonRegister);
        textView_all_user = findViewById(R.id.textGetAllUser);
    }
}