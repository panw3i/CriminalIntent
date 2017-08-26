package activitytest.example.com.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pan on 2017/8/25.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    private static final String TAG = "ppp";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置是否显示在 Fragment 上
        setHasOptionsMenu(true);
    }

    // 菜单选项的回调 , 返回值为 boolean 表示是否显示在界面上
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_new_crime:

                Crime crime = new Crime();
                CrimeLab.getCrimeLab(getActivity()).getCrimes().add(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());

                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    // 覆写方法创建选择菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

    }

    // 显示界面时更新视图
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_crime_list,container,false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 更新数据
        // updateUI();


        return view;
    }


    // 修改数据后进行 UI 的更新
    private void updateUI() {

        // 获得管理对象
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());

        // 数据源
        List<Crime> crimeList = crimeLab.getCrimes();

        // 删除标题为空的 crime
        for(Crime crime: crimeList){
            Log.i(TAG, "updateUI: " + crime);
            if (crime.getTitle() == null){
                crimeList.remove(crime);
            }
        }


        if (crimeList.size()!=0){
            if (mCrimeAdapter == null){
                mCrimeAdapter = new CrimeAdapter(crimeList);

                mCrimeRecyclerView.setAdapter(mCrimeAdapter);
            } else {
                mCrimeAdapter.notifyDataSetChanged();
            }
        }

    }

    // 视图组件内部类
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCheckBox;
        private Crime mCrime;


        // CrimeHolder 用来保存视图
        public CrimeHolder(View itemView)  {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);

            itemView.setOnClickListener(this);

        }

        // 绑定数据到组件
        public void bindCrime(Crime crime){

            mCrime = crime;

            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            mCheckBox.setChecked(crime.isSolved());


        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    // 适配器内部类
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);

        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
