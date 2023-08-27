package es.unican.iotguardian.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.RiesgoDao;
import es.unican.iotguardian.repository.db.ControlDao;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.RiesgoDao;
import es.unican.iotguardian.repository.db.ControlDao;

@Entity
public class Control implements Parcelable {
    @SerializedName("id")
    @NonNull
    @Id
    private Long idControl;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @Transient
    private boolean expanded = false;

    @Expose(deserialize = false)
    @ToMany
    @JoinEntity(
            entity = JoinRiesgosWithControles.class,
            sourceProperty = "idControl",
            targetProperty = "idRiesgo"
    )
    private List<Riesgo> mitigaRiesgos;

    private Long fk_perfil;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 695321196)
    private transient ControlDao myDao;

    public Control() {
        idControl = 0L;
        mitigaRiesgos = new ArrayList<>();
    }

    @Generated(hash = 612895255)
    public Control(@NonNull Long idControl, String nombre, String descripcion, Long fk_perfil) {
        this.idControl = idControl;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fk_perfil = fk_perfil;
    }

    @NonNull
    public Long getIdControl() {
        return idControl;
    }

    public void setIdControl(@NonNull Long idControl) {
        this.idControl = idControl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMitigaRiesgos(List<Riesgo> mitigaRiesgos) {
        this.mitigaRiesgos = mitigaRiesgos;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1409817531)
    public List<Riesgo> getMitigaRiesgos() {
        if (mitigaRiesgos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RiesgoDao targetDao = daoSession.getRiesgoDao();
            List<Riesgo> mitigaRiesgosNew = targetDao
                    ._queryControl_MitigaRiesgos(idControl);
            synchronized (this) {
                if (mitigaRiesgos == null) {
                    mitigaRiesgos = mitigaRiesgosNew;
                }
            }
        }
        return mitigaRiesgos;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1248176733)
    public synchronized void resetMitigaRiesgos() {
        mitigaRiesgos = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Override
    public String toString() {
        return "Control{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    public boolean getExpanded() {
        return this.expanded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(idControl);
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeList(mitigaRiesgos);
    }

    public Long getFk_perfil() {
        return this.fk_perfil;
    }

    public void setFk_perfil(Long fk_perfil) {
        this.fk_perfil = fk_perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Control)) return false;
        Control control = (Control) o;
        return idControl.equals(control.idControl) && nombre.equals(control.nombre) && descripcion.equals(control.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idControl, nombre, descripcion);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1336688166)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getControlDao() : null;
    }
}
