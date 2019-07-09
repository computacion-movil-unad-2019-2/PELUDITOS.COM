package com.servicios;

import com.entidades.adoptanteRespuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostServiceAdoptante {

    @GET("adoptante/")
    Call<adoptanteRespuesta> obtenerLista();

    @FormUrlEncoded
    @POST("adoptante/crear/")
    Call<String> savePost(@Field("cedula") String cedula,
                                      @Field("nombres") String nombres,
                                      @Field("apellidos") String apellidos,
                                      @Field("edad") String edad,
                                      @Field("sexo") String sexo,
                                      @Field("estadoCivil") String estadoCivil,
                                      @Field("correoElectronico") String correoElectronico,
                                      @Field("numTelefono") String numTelefono,
                                      @Field("tipoMascota") String tipoMascota
                                    );


}
