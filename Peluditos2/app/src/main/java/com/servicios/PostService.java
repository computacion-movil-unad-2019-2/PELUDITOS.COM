package com.servicios;

import com.entidades.institucionesRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    @GET("instituciones/")
    Call<institucionesRespuesta> obtenerLista();
}
