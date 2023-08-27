package es.unican.iotguardian.common;

import android.app.Application;
import android.content.Context;
import org.greenrobot.greendao.database.Database;

import es.unican.iotguardian.repository.db.DaoMaster;
import es.unican.iotguardian.repository.db.DaoSession;

public class MyApplication  extends Application {
    private DaoSession daoSession;

    @Override
    public void  onCreate() {
        super.onCreate();
        CustomOpenHelper helper = new CustomOpenHelper(this, "appwise-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static class CustomOpenHelper extends DaoMaster.OpenHelper {
        public CustomOpenHelper(Context context, String name) {
            super(context, name);
        }

        @Override
        public void onCreate(Database db) {
            super.onCreate(db);
        }
    }
}



