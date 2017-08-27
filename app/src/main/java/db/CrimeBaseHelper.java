package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by pan on 2017/8/26.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {

    public CrimeBaseHelper(Context context) {
        super(context, "crimeBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("create table crimes " +
                 "(_id integer primary key autoincrement,"+
                 "uuid,title,date,solved)"
         );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
