package es.unican.iotguardian.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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
import es.unican.iotguardian.repository.db.ControlDao;
import es.unican.iotguardian.repository.db.RiesgoDao;
import es.unican.iotguardian.repository.db.DaoSession;
import es.unican.iotguardian.repository.db.ControlDao;
import es.unican.iotguardian.repository.db.RiesgoDao;

@Entity
public class Riesgo implements Parcelable {
    @SerializedName("id")
    @NonNull
    @Id
    private Long idRiesgo;

    private String nombre;

    private String descripcion;

    @ToMany
    @JoinEntity(
            entity = JoinRiesgosWithControles.class,
            sourceProperty = "idRiesgo",
            targetProperty = "idControl"
    )
    private List<Control> controles;

    @Transient
    private boolean expanded = false;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1038012363)
    private transient RiesgoDao myDao;

    public Riesgo() {
        idRiesgo = 0L;
        controles = new ArrayList<>();
    }

    @Generated(hash = 365859718)
    public Riesgo(@NonNull Long idRiesgo, String nombre, String descripcion) {
        this.idRiesgo = idRiesgo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    protected Riesgo(Parcel in) {
        if (in.readByte() == 0) {
            idRiesgo = null;
        } else {
            idRiesgo = in.readLong();
        }
        nombre = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<Riesgo> CREATOR = new Creator<Riesgo>() {
        @Override
        public Riesgo createFromParcel(Parcel in) {
            return new Riesgo(in);
        }

        @Override
        public Riesgo[] newArray(int size) {
            return new Riesgo[size];
        }
    };

    @NonNull
    public Long getIdRiesgo() {
        return idRiesgo;
    }

    public void setIdRiesgo(@NonNull Long idRiesgo) {
        this.idRiesgo = idRiesgo;
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

    public void setControles(List<Control> controles) {
        this.controles = controles;
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
    @Generated(hash = 771935241)
    public List<Control> getControles() {
        if (controles == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ControlDao targetDao = daoSession.getControlDao();
            List<Control> controlesNew = targetDao._queryRiesgo_Controles(idRiesgo);
            synchronized (this) {
                if (controles == null) {
                    controles = controlesNew;
                }
            }
        }
        return controles;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 787308186)
    public synchronized void resetControles() {
        controles = null;
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
        return "Riesgo{" +
                "idRiesgo=" + idRiesgo +
                ", nombre='" + nombre + '\'' +
                ", controles=" + controles +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(idRiesgo);
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeList(controles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Riesgo)) return false;
        Riesgo riesgo = (Riesgo) o;
        return idRiesgo.equals(riesgo.idRiesgo) && nombre.equals(riesgo.nombre) && descripcion.equals(riesgo.descripcion) && controles.equals(riesgo.controles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRiesgo, nombre, descripcion, controles);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1964751526)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRiesgoDao() : null;
    }
}
