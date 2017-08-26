package activity;

import android.support.v4.app.Fragment;

import fragment.CrimeListFragment;

/**
 * Created by pan on 2017/8/25.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
