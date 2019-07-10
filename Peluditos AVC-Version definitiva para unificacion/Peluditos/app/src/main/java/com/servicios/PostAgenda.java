package com.servicios;

import com.entidades.agendaRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostAgenda {

    @GET("agenda/")
    Call<agendaRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("agenda/crear/")
    Call<String> savePost(@Field("mensaje") String mensaje,
                          @Field("mascota") String mascota,
                          @Field("adoptante") String adoptante,
                          @Field("fechaAgenda") String fechaAgenda,
                          @Field("hora") String hora,
                          @Field("ubicacion") String ubicacion
    );
}
