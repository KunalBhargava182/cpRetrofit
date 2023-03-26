package com.gjr.cpretrofit.api;

import com.gjr.cpretrofit.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerInterface {
    String LOGIN_URL = "http://kunalwork.42web.io/cp/";
    //String LOGIN_URL = "http://sammankar.infinityfreeapp.com/app/";
    // String LOGIN_URL = "http://harish.42web.io/retrofit/";

    @FormUrlEncoded
    @POST("simplelogin.php")
    Call<User> getUserLogin(
            @Field("users_email") String users_email,
            @Field("users_password") String users_password
    );
    @FormUrlEncoded
    @POST("simpleregister.php")
    Call<User> getUserRegister(
            @Field("users_name") String users_name,
            @Field("users_email") String users_email,
            @Field("users_mobile") String users_mobile,
            @Field("users_password") String users_password
    );
    @GET("getusers.php")
    Call<List<User>> getsuperHeroes();

}