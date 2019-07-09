package com.servicios;

import com.entidades.denunciamaltratoRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceSeguimientodenunciamaltrato {

    //@GET("denunciamaltrato/")
    //Call<denunciamaltratoRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("denunciamaltrato/crearseguimiento/")
    Call<String> savePost(@Field("denunciaid") String id,
                          @Field("id") String ubicacion,
                          @Field("seguimiento") String descripcion

    );


    void execute();
}
