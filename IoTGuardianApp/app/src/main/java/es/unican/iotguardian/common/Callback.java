package es.unican.iotguardian.common;

/**
 * Clase callback personalizada.
 * Es usada en llamadas asincronas al repositorio.
 * @param <T> tipo de los datos
 */
public interface Callback<T> {

    /**
     * This method is called when the data is successfully fetched
     * @param data the data fetched
     */
    void onSuccess(T data);

    /**
     * This method is called when a failure occurred
     */
    void onFailure();

}
