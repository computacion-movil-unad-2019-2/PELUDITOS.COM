package com.servicios;

import com.entidades.agendaRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostAgenda {

    @GET("agenda/")
    Call<agendaRespuesta> obtenerLista();
}
