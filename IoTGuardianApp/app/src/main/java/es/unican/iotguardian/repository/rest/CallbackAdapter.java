package es.unican.iotguardian.repository.rest;

import es.unican.iotguardian.common.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Esta clase adapta un Callback de Retrofit a nuestro Callback personalizado.
 * @param <T>
 */
class CallbackAdapter<T> implements retrofit2.Callback<T> {
    private final Callback<T> cb;

    public CallbackAdapter(Callback<T> cb) {
        this.cb = cb;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        cb.onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        cb.onFailure();
    }
}