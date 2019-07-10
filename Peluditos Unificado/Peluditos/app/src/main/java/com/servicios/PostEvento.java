package com.servicios;

import com.entidades.eventoRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostEvento {

    @GET("evento/")
    Call<eventoRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("evento/crear/")
    Call<String> savePost(@Field("codigo") String codigo,
                          @Field("nombre") String nombre,
                          @Field("lugar") String lugar,
                          @Field("hora") String hora,
                          @Field("fecha") String fecha
    );
}
