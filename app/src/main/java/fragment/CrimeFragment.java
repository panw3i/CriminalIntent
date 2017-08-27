package fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import activitytest.example.com.criminalintent.R;
import model.Crime;
import model.CrimeLab;

/**
 * Created by pan on 2017/8/24.
 */

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private TextView mTitle;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    private CrimeLab mCrimeLab;
    private List<Crime> mCrimes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCrime = new Crime();

        UUID uuid = (UUID) getArguments().getSerializable("crime_id");
        mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(uuid);

        setHasOptionsMenu(true);
        //mCrimes = CrimeLab.getCrimeLab(getActivity()).getCrimes();

    }

    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.getCrimeLab(getActivity()).updateCrime(mCrime);
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

                if (s != ""){
                    mCrime.setTitle(s.toString());
                }

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



    // 不同 Activity 之间的 Fragment 进行数据的传递使用 Bundle 对象
    public static CrimeFragment newInstance(UUID crimeId){

        // 创建 Bundle 对象
        Bundle args = new Bundle();

        // Bundle 对象的 putXxx()方法进行(键,值)的存储
        args.putSerializable("crime_id",crimeId);

        // 将值当前的 Fragment 中 , 作为对象传递给其它类 , 用来接受 UUID 这个数据
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        // 最后返回一个新的包含了相关数据的 Fragment 对象
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

    // 创建右上角菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_delete,menu);
    }

    // 配置右上角菜单选项并显示


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                deleteCrime();
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteCrime() {
        CrimeLab.getCrimeLab(getActivity()).getCrimes().remove(mCrime);

    }
}
