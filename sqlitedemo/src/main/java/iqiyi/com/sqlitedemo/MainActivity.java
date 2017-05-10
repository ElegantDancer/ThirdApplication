package iqiyi.com.sqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity---->";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();

        writableDatabase.execSQL("insert into " + Constants.NAME + "(name , age) values ('zhaozhenzhen', 27)");

        Cursor cursor = writableDatabase.rawQuery("select * from " + Constants.NAME, null);

        while (cursor.moveToNext()){
            int personId = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            Log.i(TAG, "person id is " + personId + " name is : " + name + "age is : " + age);
        }

        cursor.close();
        writableDatabase.close();
    }
}
