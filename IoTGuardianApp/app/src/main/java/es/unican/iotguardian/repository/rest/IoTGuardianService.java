package es.unican.iotguardian.repository.rest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import es.unican.iotguardian.common.Callback;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.Riesgo;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase para acceder a los recursos del servicio REST IoTGuardian usando Retrofit.
 */
public class IoTGuardianService {

    private static IoTGuardianAPI api;

    private static IoTGuardianAPI getAPI() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IoTGuardianServiceConstants.getAPIURL())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(IoTGuardianAPI.class);
        }
        return api;
    }

    public static final long TIMEOUT_SECONDS = 60L;

    /**
     * Descarga las dispositivos de la API REST de forma asincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     * @param categoria se usa si se quiere que las dispositivos se filtren por categoria,
     *                  sino dejarlo a NULL
     */
    public static void requestDispositivos(Callback<DispositivoIoT[]> cb, String categoria) {
        final Call<DispositivoIoT[]> call = getAPI().dispositivos(categoria);
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga las dispositivos de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @param categoria se usa si se quiere que las dispositivos se filtren por categoria,
     *                  sino dejarlo a NULL
     * @return el objeto response que contiene las dispositivos
     */
    public static DispositivoIoT[] getDispositivos(String categoria) {
        final Call<DispositivoIoT[]> call = getAPI().dispositivos(categoria);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<DispositivoIoT[]> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        // Espera a que acaben las tareas en background
        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Si hubo algun problema, response es null
        return  runnable.getResponse();
    }

    /**
     * Descarga un dispositivo de la API REST de forma asincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     * @param nombre de el dispositivo que se quiere descargar
     */
    public static void requestDispositivo(Callback<DispositivoIoT> cb, String nombre) {
        final Call<DispositivoIoT> call = getAPI().dispositivo(nombre);
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga un dispositivo de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @param nombre de el dispositivo que se quiere descargar
     * @return el objeto response que contiene el dispositivo
     */
    public static DispositivoIoT getDispositivo(String nombre) {
        final Call<DispositivoIoT> call = getAPI().dispositivo(nombre);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<DispositivoIoT> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return  runnable.getResponse();
    }

    /**
     * Descarga los riesgos de la API REST de forma sincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     */
    public static void requestRiesgos(Callback<Riesgo[]> cb) {
        final Call<Riesgo[]> call = getAPI().riesgos();
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga los riesgos de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @return el objeto response que contiene las aplicaciones
     */
    public static Riesgo[] getRiesgos() {
        final Call<Riesgo[]> call = getAPI().riesgos();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<Riesgo[]> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return runnable.getResponse();
    }

    /**
     * Descarga un riesgo de la API REST de forma asincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     * @param id del riesgo que se quiere descargar
     */
    public static void requestRiesgo(Callback<Riesgo> cb, Long id) {
        final Call<Riesgo> call = getAPI().riesgo(id);
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga un riesgo de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @param id del riesgo que se quiere descargar
     * @return el objeto response que contiene el riesgo
     */
    public static Riesgo getRiesgo(Long id) {
        final Call<Riesgo> call = getAPI().riesgo(id);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<Riesgo> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return  runnable.getResponse();
    }

    /**
     * Descarga los controles de la API REST de forma sincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     */
    public static void requestControles(Callback<Control[]> cb) {
        final Call<Control[]> call = getAPI().controles();
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga los controles de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @return el objeto response que contiene los controles
     */
    public static Control[] getControles() {
        final Call<Control[]> call = getAPI().controles();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<Control[]> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return runnable.getResponse();
    }

    /**
     * Descarga un control de la API REST de forma asincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     * @param id del control que se quiere descargar
     */
    public static void requestControl(Callback<Control> cb, Long id) {
        final Call<Control> call = getAPI().control(id);
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga un control de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @param id del control que se quiere descargar
     * @return el objeto response que contiene el riesgo
     */
    public static Control getControl(Long id) {
        final Call<Control> call = getAPI().control(id);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<Control> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return  runnable.getResponse();
    }

    /**
     * Descarga las categorias de la API REST de forma sincrona.
     * Ejecuta la llamada en un hilo en segundo plano y notifica el resultado a
     * través del Callback proporcionado.
     * @param cb el callback que procesa la respuesta de forma asincrona
     */
    public static void requestCategorias(Callback<Categoria[]> cb) {
        final Call<Categoria[]> call = getAPI().categorias();
        call.enqueue(new CallbackAdapter<>(cb));
    }

    /**
     * Descarga las categorias de la API REST de forma sincrona.
     * Bloquea el hilo actual hasta que se complete la llamada y se obtenga la respuesta.
     * @return el objeto response que contiene las aplicaciones
     */
    public static Categoria[] getCategorias() {
        final Call<Categoria[]> call = getAPI().categorias();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CallRunnable<Categoria[]> runnable = new CallRunnable<>(call);
        executor.execute(runnable);

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return runnable.getResponse();
    }
}
