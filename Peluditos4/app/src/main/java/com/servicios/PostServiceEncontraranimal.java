package com.servicios;

import com.entidades.encontraranimalRespuesta;
import com.entidades.mascotaRespuesta;
import com.entidades.tipoMascotaRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceEncontraranimal {

    @GET("encontraranimal/")
    Call<encontraranimalRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("encontraranimal/crear/")
    Call<String> savePost(@Field("id") String id,
                          @Field("ubicacion") String ubicacion,
                          @Field("raza") String raza,
                          @Field("nombremascota") String nombremascota,
                          @Field("caracteristicas") String caracteristicas

    );


    void execute();
}
