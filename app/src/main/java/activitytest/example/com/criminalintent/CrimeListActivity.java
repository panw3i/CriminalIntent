package activitytest.example.com.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by pan on 2017/8/25.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
