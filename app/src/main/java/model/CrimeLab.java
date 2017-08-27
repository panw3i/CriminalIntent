package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import db.CrimeBaseHelper;

/**
 * Created by pan on 2017/8/24.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public CrimeLab(Context context) {

        // 创建数据库对象
        mContext = context.getApplicationContext();  // ?
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

        Cursor cursor = mDatabase.rawQuery("select * from crimes",null);

        mCrimes = new ArrayList<>();

        while (cursor.moveToNext()){

            String uuidString = cursor.getString(0);
            String title = cursor.getString(1);
            long date = cursor.getLong(2);
            int isSolved = cursor.getInt(3);

            Crime crime = new Crime(UUID.fromString(uuidString));
            crime.setTitle(title);
            crime.setDate(new Date(date));
            crime.setSolved(isSolved!=0);

            mCrimes.add(crime);

        }

        cursor.close();



    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }

        return null;
    }

    public static CrimeLab getCrimeLab(Context context){
        if (sCrimeLab ==null){
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;

    }

    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert("crimes",null,values);
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update("crimes",values,"uuid=?",new String[]{uuidString});
    }

    // 根据 crime 创建 ContentValues 对象
    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put("uuid",crime.getId().toString());
        contentValues.put("title",crime.getTitle());
        contentValues.put("date",crime.getDate().getTime());
        contentValues.put("solved",crime.isSolved());

        return contentValues;
    }


}
