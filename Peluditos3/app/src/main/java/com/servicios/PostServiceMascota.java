package com.servicios;

import com.entidades.mascota;
import com.entidades.mascotaRespuesta;
import com.entidades.tipoMascotaRespuesta;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PostServiceMascota {

    @GET("mascota/")
    Call<mascotaRespuesta> obtenerLista();

    @GET("mascota/listadoTipo")
    Call<tipoMascotaRespuesta> obtenerListaTipo();

    @FormUrlEncoded
    @POST("mascota/crear/")
    Call<String> savePost(@Field("id") String id,
                          @Field("nombres") String nombres,
                          @Field("edad") String edad,
                          @Field("tamano") String tamano,
                          @Field("controlMedico") String controlMedico,
                          @Field("ciudadReferencia") String ciudadReferencia,
                          @Field("ubicacion") String ubicacion,
                          @Field("tipo") String tipo,
                          @Field("estado") String estado
    );

    @GET("mascota/consulta")
    Call<mascotaRespuesta> obtenerListaId(@Field("codigo") int codigo);
}
