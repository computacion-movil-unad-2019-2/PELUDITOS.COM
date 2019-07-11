package com.servicios;

import com.entidades.dineroRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostDonacionDinero {
    @GET("donaciondinero/")
    Call<dineroRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("donaciondinero/crear/")
    Call<String> savePost(@Field("nit") String nit,
                          @Field("razonSocial") String razonSocial,
                          @Field("cuenta") String cuenta,
                          @Field("banco") String banco
    );
}
