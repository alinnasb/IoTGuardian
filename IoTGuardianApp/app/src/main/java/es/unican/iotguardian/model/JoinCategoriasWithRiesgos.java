package es.unican.iotguardian.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JoinCategoriasWithRiesgos {
    @Id(autoincrement = true)
    private Long id;
    private Long idCategoria;
    private Long idRiesgo;

    @Generated(hash = 125672077)
    public JoinCategoriasWithRiesgos(Long id, Long idCategoria, Long idRiesgo) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.idRiesgo = idRiesgo;
    }

    @Generated(hash = 389915537)
    public JoinCategoriasWithRiesgos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdRiesgo() {
        return idRiesgo;
    }

    public void setIdRiesgo(Long idRiesgo) {
        this.idRiesgo = idRiesgo;
    }
}
