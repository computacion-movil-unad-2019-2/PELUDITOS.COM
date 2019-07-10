package com.servicios;

import com.entidades.eventoRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostDonacionEspecie {



    @FormUrlEncoded
    @POST("donacion/crear/")
    Call<String> savePost(@Field("producto") String producto,
                          @Field("descripcion") String descripcion,
                          @Field("fecha") String fecha,
                          @Field("lugar") String lugar,
                          @Field("cantidad") String cantidad
    );
}
