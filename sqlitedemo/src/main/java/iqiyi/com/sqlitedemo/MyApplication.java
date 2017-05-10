package iqiyi.com.sqlitedemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by zhenzhen on 2017/3/23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
