package com.servicios;

import com.entidades.veterinariaRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostVeterinaria {
    @GET("veterinaria/")
    Call<veterinariaRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("veterinaria/crear/")
    Call<String> savePost(@Field("nit") String nit,
                          @Field("razonSocial") String razonSocial,
                          @Field("objetivo") String objetivo,
                          @Field("ubicacion") String ubicacion,
                          @Field("tipo") String tipo
    );
}
