package es.unican.iotguardian.repository.rest;

import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.Riesgo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *  Acceso a IoTGuardian API usando Retrofit.
 */
public interface IoTGuardianAPI {
    @GET("dispositivos")
    Call<DispositivoIoT[]> dispositivos(@Query("categoria") String categoria);

    @GET("dispositivos/{nombre}")
    Call<DispositivoIoT> dispositivo(@Path("nombre") String nombre);

    @GET("riesgos")
    Call<Riesgo[]> riesgos();

    @GET("riesgos/{id}")
    Call<Riesgo> riesgo(@Path("id") Long id);

    @GET("controles")
    Call<Control[]> controles();

    @GET("controles/{id}")
    Call<Control> control(@Path("id") Long id);

    @GET("categorias")
    Call<Categoria[]> categorias();
}
