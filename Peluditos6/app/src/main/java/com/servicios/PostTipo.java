package com.servicios;

import com.entidades.tipoRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostTipo {

    @GET("tipos/")
    Call<tipoRespuesta> obtenerLista();
}
