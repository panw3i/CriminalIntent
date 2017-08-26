package activitytest.example.com.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Date;
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
        //mDateButton.setEnabled(false);


        // 弹出日期选择窗口的 fragment
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();

                //DialogFragment fragment = new DialogFragment();
                // 通过目录 fragment 的静态方法将数据保存到 bundle 对象中
                DialogFragment fragment = DialogFragment.newInstance(mCrime.getDate());

                // 设置目标 fragment 交给 FragmentManger 进行管理 , 0 为请示码
                fragment.setTargetFragment(CrimeFragment.this,0);

                fragment.show(fm,"DialogDate");
            }
        });

        // TextView 的监听事件
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == 0){
            Date date = (Date) data.getSerializableExtra("data");
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate().toString());
        }
    }
}
