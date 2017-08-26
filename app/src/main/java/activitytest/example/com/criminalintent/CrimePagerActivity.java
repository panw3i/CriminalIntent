package activitytest.example.com.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by pan on 2017/8/25.
 */

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crime_pager);

        // 在启动时获得实体类的 UUID
        UUID crimeId = (UUID) getIntent().getSerializableExtra("id");


        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // 数据
        mCrimes = CrimeLab.getCrimeLab(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();

        // 为 ViewPager 设置适配器
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);

                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    // 在本类中封装一个创建 Intent对象的静态方法,用来接受其它类传入 UUID
    public static Intent newIntent(Context context, UUID crimeId){
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra("id",crimeId);
        return intent;
    }
}
