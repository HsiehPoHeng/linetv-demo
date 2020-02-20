package com.linetv.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.linetv.demo.data.db.DaoMaster;
import com.linetv.demo.data.db.DaoSession;
import com.linetv.demo.data.db.DatabaseUpgradeHelper;

public class DramasSampleApplication extends Application {

    public static boolean showmsg = false;
    private static Context context;
    private static RxSharedPreferences rxPreferences;
    private static final String DB_NAME = "dramas.db";
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initGreenDao();
        initRxSharedPreferences();
    }

    private void initGreenDao() {
        DaoMaster.OpenHelper helper= new DatabaseUpgradeHelper(this, DB_NAME);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        mDaoSession = daoMaster.newSession();
    }

    private void initRxSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        rxPreferences = RxSharedPreferences.create(preferences);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static RxSharedPreferences getRxPreferences() {
        return rxPreferences;
    }
}
