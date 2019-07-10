package com.servicios;

import com.entidades.tipoInstitucionRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostTipoInstitucion {
    @GET("TipoInstitucion/")
    Call<tipoInstitucionRespuesta> obtenerLista();
}
