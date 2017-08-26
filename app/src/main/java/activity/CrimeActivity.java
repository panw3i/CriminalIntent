package activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import fragment.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra("id");
        return CrimeFragment.newInstance(crimeId);

    }

    public static Intent newIntent(Context context,UUID crimeId){
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra("id",crimeId);
        return intent;
    }
}