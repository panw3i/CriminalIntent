package activitytest.example.com.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by pan on 2017/8/24.
 */

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private TextView mTitle;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCrime = new Crime();

        UUID uuid = (UUID) getArguments().getSerializable("crime_id");
        mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(uuid);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime,container,false);

        mTitle = (TextView) view.findViewById(R.id.crime_title);

        mTitle.setText(mCrime.getTitle());

        // 先设置时间按纽为禁用状态 并设定实例对象创建时的日期
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        // CheckBox 的监听事件
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return view;

    }

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable("crime_id",crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
