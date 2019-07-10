package com.servicios;

import com.entidades.denunciamaltratoRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceSeguimientodenunciamaltrato {

    @GET("seguimientodenuncia/")
    //Call<denunciamaltratoRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST(value = "seguimientodenuncia/crear/")
    Call<String> savePost(@Field("id") String id,
                          @Field("seguimiento") String seguimiento


    );


    void execute();
}
