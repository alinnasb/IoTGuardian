package es.unican.iotguardian.activities.dispositivos.search;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import es.unican.iotguardian.model.DispositivoIoT;
import es.unican.iotguardian.model.Categoria;
import es.unican.iotguardian.model.Perfil;
import es.unican.iotguardian.repository.db.CategoriaDao;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.DispositivoIoTDao;
import es.unican.iotguardian.repository.db.PerfilDao;

public class SearchResultPresenter implements ISearchResultContract.Presenter {

    private final ISearchResultContract.View view;
    private DispositivoIoTDao dispositivoIoTDao;
    private CategoriaDao categoriaDao;
    private PerfilDao perfilDao;

    public SearchResultPresenter(ISearchResultContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        DaoSession daoSession = view.getMyApplication().getDaoSession();
        dispositivoIoTDao = daoSession.getDispositivoIoTDao();
        categoriaDao = daoSession.getCategoriaDao();
        perfilDao = daoSession.getPerfilDao();
    }

    @Override
    public List<DispositivoIoT> doSearch(String query) {
        String modifiedQuery = "%" + removeAccents(query.trim().toLowerCase()) + "%";

        List<Categoria> categorias = categoriaDao.loadAll();
        List<Long> categoriaIds = new ArrayList<>();
        for (Categoria c : categorias) {
            String nombre = removeAccents(c.getNombre().trim().toLowerCase());
            if (nombre.contains(removeAccents(query.trim().toLowerCase()))) {
                categoriaIds.add(c.getIdCategoria());
            }
        }

        return dispositivoIoTDao.queryBuilder()
                .whereOr(
                        DispositivoIoTDao.Properties.Nombre.like(modifiedQuery),
                        DispositivoIoTDao.Properties.Fk_categoria.in(categoriaIds)
                ).list();
    }

    @Override
    public DispositivoIoT getAppByName(String appName) {
        return dispositivoIoTDao.queryBuilder().where(DispositivoIoTDao.Properties.Nombre.like(appName)).unique();
    }

    @Override
    public List<DispositivoIoT> getPerfilApps() {
        Perfil perfil = Perfil.getInstance(perfilDao);
        return perfil.getAppsAnhadidas();
    }

    private String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
