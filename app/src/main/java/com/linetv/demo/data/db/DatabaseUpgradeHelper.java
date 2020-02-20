package com.linetv.demo.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

public class DatabaseUpgradeHelper extends DaoMaster.OpenHelper {


    public DatabaseUpgradeHelper(Context context, String name) {
        super(context, name);
        Log.i("greenDAO","DatabaseUpgradeHelper");
    }

    public DatabaseUpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " +
                oldVersion + " to " + newVersion + " by migrating all tables data");
        MigrationHelper.getInstance().migrate(db, DramasTableDao.class);
    }
}
