package com.gjr.cpretrofit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gjr.cpretrofit.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class GetUserActivityResponse extends AppCompatActivity {
    TextView textView_user;
    ArrayList<User> userArrayList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user);
        textView_user = findViewById(R.id.textViewUser);
        Gson gson = new GsonBuilder().setLenient().create();


  /*      Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInterface.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        ServerInterface api = retrofit.create(ServerInterface.class);
        Call<ResponseBody> listCall = api.getusers();

        listCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                ResponseBody responseBody = response.body();
                if (response.isSuccessful())
                {
                    assert responseBody != null;
                    System.out.println("TATA VAL "+responseBody.toString());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                System.out.println("HORN OK "+t.getMessage());
            }
        });*/
    }
}