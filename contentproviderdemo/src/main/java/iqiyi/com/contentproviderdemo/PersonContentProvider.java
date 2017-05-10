package iqiyi.com.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by zhenzhen on 2017/3/24.
 */

public class PersonContentProvider extends ContentProvider {


    /**
     * 数据类型的MIME类型字符串 则应该以vnd.android.cursor.dir/开头
     */
    public static final String PERSONS_TYPE = "vnd.android.cursor.dir/person";

    /**
     * 单一数据的MIME类型字符串应该以 vnd.android.cursor.item/开头
     */
    public static final String PERSONS_ITEM_TYPE = "vnd.android.cursor.item/person";
    public static final String AUTHORITY = "com.zhenzhen.personprovider";

    /**
     * 自定义识别码
     */

    public static final int PERSONS = 1;
    public static final int PERSON = 2;

    public static final Uri PERSONS_URI = Uri.parse("content://" + AUTHORITY + "/person");

    private DataBaseHelper dataBaseHelper = null;

    /**
     *
     */

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //匹配路径 content://com.zhenzhen.personprovider/person  返回code是 PERSONS
        uriMatcher.addURI(AUTHORITY, "person", PERSONS);
        //匹配路径 content://com.zhenzhen.personprovider/person/#  (#可以为任何id) 返回code是 PERSON
        uriMatcher.addURI(AUTHORITY, "person/#", PERSON);
    }

    @Override
    public boolean onCreate() {

        dataBaseHelper = new DataBaseHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)){
            case PERSONS:
                return database.query(Constants.NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case PERSON:
                long personId = ContentUris.parseId(uri);
                String where = "id=" + personId; //获取指定id的记录

                //把其它的 selection条件加上

                where += TextUtils.isEmpty(selection) ? "" : "and (" + selection + ")";

                return database.query(Constants.NAME, projection, where, selectionArgs, null, null, sortOrder);
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        /**
         * 告诉系统是单条数据 还是 多条数据  前面的部分是固定写法
         */

        switch (uriMatcher.match(uri)){
            case PERSONS:
                return PERSONS_TYPE;
            case PERSON:
                return PERSONS_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("unkown uri: " + uri );
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
