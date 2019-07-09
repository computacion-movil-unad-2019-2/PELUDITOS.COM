package com.servicios;

import com.entidades.denunciamaltratoRespuesta;
import com.entidades.encontraranimalRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceDenunciamaltrato {

    @GET("denunciamaltrato/")
    Call<denunciamaltratoRespuesta> obtenerLista();
    @FormUrlEncoded
    @POST("denunciamaltrato/ crearseguimiento/")
    Call<String> savePost(@Field("id") String id,
                          @Field("ubicacion") String ubicacion,
                          @Field("descripcion") String descripcion

    );



    void execute();


}
