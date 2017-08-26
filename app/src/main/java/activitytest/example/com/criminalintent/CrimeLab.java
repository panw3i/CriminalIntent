package activitytest.example.com.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pan on 2017/8/24.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

//        for (int i = 0; i < 50; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Crime # " + i);
//            crime.setSolved(i%2 == 0);
//            mCrimes.add(crime);
//        }
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

    public void addCrime(Crime crime){
        mCrimes.add(crime);
    }


}
