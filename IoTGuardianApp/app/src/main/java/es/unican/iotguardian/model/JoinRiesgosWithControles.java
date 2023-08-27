package es.unican.iotguardian.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JoinRiesgosWithControles {
    @Id(autoincrement = true)
    private Long id;
    private Long idRiesgo;
    private Long idControl;

    @Generated(hash = 248395386)
    public JoinRiesgosWithControles(Long id, Long idRiesgo, Long idControl) {
        this.id = id;
        this.idRiesgo = idRiesgo;
        this.idControl = idControl;
    }

    @Generated(hash = 1505888283)
    public JoinRiesgosWithControles() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRiesgo() {
        return idRiesgo;
    }

    public void setIdRiesgo(Long idRiesgo) {
        this.idRiesgo = idRiesgo;
    }

    public Long getIdControl() {
        return idControl;
    }

    public void setIdControl(Long idControl) {
        this.idControl = idControl;
    }
}
