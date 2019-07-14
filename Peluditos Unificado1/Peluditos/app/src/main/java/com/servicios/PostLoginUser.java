package com.servicios;

import com.entidades.usuarioRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostLoginUser {

    @GET("usuario/")
    Call<usuarioRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("usuario/crear/")
    Call<String> savePost(@Field("id") String id,
                          @Field("user") String user,
                          @Field("nombres") String nombres,
                          @Field("pwd") String pwd,
                          @Field("documento") String documento

    );
}
