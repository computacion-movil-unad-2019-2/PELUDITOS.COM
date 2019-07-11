package com.servicios;

import com.entidades.expRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostExperiencia {
    @GET("experiencia/")
    Call<expRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("experiencia/crear/")
    Call<String> savePost(@Field("vivencia") String vivencia,
                          @Field("lugar") String lugar
    );
}
