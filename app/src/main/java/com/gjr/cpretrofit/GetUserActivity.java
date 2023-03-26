package com.gjr.cpretrofit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gjr.cpretrofit.api.RetrofitClient;
import com.gjr.cpretrofit.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserActivity extends AppCompatActivity {
    ListView superListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user1);
        superListView = findViewById(R.id.superListView);

        getSuperHeroes();
    }

    private void getSuperHeroes() {
        Call<List<User>> call = RetrofitClient.getInstance().getMyApi().getsuperHeroes();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                List<User> myheroList = response.body();
                assert myheroList != null;
                String[] oneHeroes = new String[myheroList.size()];

                for (int i = 0; i < myheroList.size(); i++) {
                    oneHeroes[i] = myheroList.get(i).getUsersName();
                }

                superListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, oneHeroes));
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                System.out.println("TATA E "+t.getMessage());
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

}