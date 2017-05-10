package iqiyi.com.contentproviderdemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhenzhen on 2017/3/23.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DataBaseHelper.class.getSimpleName();

    private static final String NAME = "testDb";//database name
    private static final int VERSION = 1; // database version

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 这种方式 能够有效控制数据库的默认名字和 版本
     * @param context
     */
    public DataBaseHelper(Context context){

        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 第一次创建的时候 需要执行的一些初始化工作
         * 第一次发布版本的时候  如果后面数据库有更新，则需要在 onUpgrade中进行
         */

        db.execSQL("create table if not exists " + Constants.NAME + "(id INTEGER PRIMARY KEY, name varchar, age INTEGER) ");
        Log.i(TAG, "-----> onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
