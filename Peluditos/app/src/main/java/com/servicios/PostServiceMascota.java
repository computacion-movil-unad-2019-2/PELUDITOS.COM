package com.servicios;

import com.entidades.mascotaRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceMascota {

    @GET("mascota/")
    Call<mascotaRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("mascota/crear/")
    Call<String> savePost(@Field("id") String id,
                          @Field("nombres") String nombres,
                          @Field("edad") String edad,
                          @Field("tamano") String tamano,
                          @Field("controlMedico") String controlMedico,
                          @Field("ciudadReferencia") String ciudadReferencia,
                          @Field("ubicacion") String ubicacion
    );
}
