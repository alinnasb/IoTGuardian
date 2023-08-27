package es.unican.iotguardian.repository;

import es.unican.iotguardian.common.Callback;
import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.model.Control;
import es.unican.iotguardian.model.Riesgo;

/**
 * Un Repository para acceder a los recursos de IoTGuardianService
 */
public interface IIoTGuardinRepository {

    /**
     * Solicita dispositivos de forma asincrona.
     * Una vez que los dispositivos han sido recuperados de la fuente,
     * el callback indicado es llamado. Persiste los dispositivos
     * en la base de datos local.
     * @param cb callback que procesa la respuesta de forma asíncrona
     * @param categoria se utiliza para filtrar las dispositivos por categoría
     *                  (opcional, se puede dejar a null)
     */
    void requestDispositivos(Callback<DispositivoIoT[]> cb, String categoria);

    /**
     * Solicita dispositivos de forma sincrona.
     * Este metodo retorna una lista de dispositivos directamente. Persiste las dispositivos
     * en la base de datos local.
     * @param categoria
     * @return la lista de dispositivos
     *          null si ocurre un error
     */
    DispositivoIoT[] getDispositivos(String categoria);

    /**
     * Solicita un dispositivo de forma asincrona.
     * Una vez que el dispositivo ha sido recuperada de la fuente,
     * el callback indicado es llamado.
     * @param cb callback que procesa la respuesta de forma asíncrona
     */
    void requestDispositivo(Callback<DispositivoIoT> cb, String nombre);

    /**
     * Solicita un dispositivo de forma sincrona.
     * Este metodo retorna un dispositivo directamente.
     * @return el dispositivo
     *          null si ocurre un error
     */
    DispositivoIoT getDispositivo(String nombre);

    /**
     * Solicita riesgos de forma asincrona.
     * Una vez que los riesgos han sido recuperadas de la fuente,
     * el callback indicado es llamado. Persiste los riesgos
     * en la base de datos local.
     * @param cb callback que procesa la respuesta de forma asíncrona
     */
    void requestRiesgos(Callback<Riesgo[]> cb);

    /**
     * Solicita riesgos de forma sincrona.
     * Este metodo retorna una lista de riesgos directamente. Persiste los riesgos
     * en la base de datos local.
     * @return la lista de riesgos
     *          null si ocurre un error
     */
    Riesgo[] getRiesgos();

    /**
     * Solicita un riesgo de forma asincrona.
     * Una vez que el riesgo ha sido recuperado de la fuente,
     * el callback indicado es llamado.
     * @param cb callback que procesa la respuesta de forma asíncrona
     */
    void requestRiesgo(Callback<Riesgo> cb, Long id);

    /**
     * Solicita un riesgo de forma sincrona.
     * Este metodo retorna un riesgo directamente.
     * @return el riesgo
     *          null si ocurre un error
     */
    Riesgo getRiesgo(Long id);

    /**
     * Solicita controles de forma asincrona.
     * Una vez que los controles han sido recuperadas de la fuente,
     * el callback indicado es llamado. Persiste los controles
     * en la base de datos local.
     * @param cb callback que procesa la respuesta de forma asíncrona
     */
    void requestControles(Callback<Control[]> cb);

    /**
     * Solicita controles de forma sincrona.
     * Este metodo retorna una lista de controles directamente. Persiste los controles
     * en la base de datos local.
     * @return la lista de controles
     *          null si ocurre un error
     */
    Control[] getControles();

    /**
     * Solicita un control de forma asincrona.
     * Una vez que el control ha sido recuperado de la fuente,
     * el callback indicado es llamado.
     * @param cb callback que procesa la respuesta de forma asíncrona
     */
    void requestControl(Callback<Control> cb, Long id);

    /**
     * Solicita un control de forma sincrona.
     * Este metodo retorna un control directamente.
     * @return el control
     *          null si ocurre un error
     */
    Control getControl(Long id);

    /**
     * Solicita categorias de forma asincrona.
     * Una vez que las categorias han sido recuperadas de la fuente,
     * el callback indicado es llamado. Persiste las categorias
     * en la base de datos local.
     * @param cb callback que procesa la respuesta de forma asíncrona
     */
    void requestCategorias(Callback<Categoria[]> cb);

    /**
     * Solicita categorias de forma sincrona.
     * Este metodo retorna una lista de categorias directamente. Persiste las categorias
     * en la base de datos local.
     * @return la lista de categorias
     *          null si ocurre un error
     */
    Categoria[] getCategorias();
}
