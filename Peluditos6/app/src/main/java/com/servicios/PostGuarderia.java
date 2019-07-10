package com.servicios;
import com.entidades.guarderiaRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostGuarderia {
    @GET("guarderia/")
    Call<guarderiaRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("guarderia/crear/")
    Call<String> savePost(@Field("nit") String nit,
                          @Field("razonSocial") String razonSocial,
                          @Field("objetivo") String objetivo,
                          @Field("ubicacion") String ubicacion,
                          @Field("tipo") String tipo
    );
}
