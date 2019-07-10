package com.servicios;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostCalificarServicio {

    @FormUrlEncoded
    @POST("califica/crear/")
    Call<String> savePost(@Field("calificacion") String calificacion
    );
}
