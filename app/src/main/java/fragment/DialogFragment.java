package fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import activitytest.example.com.criminalintent.R;

/**
 * Created by pan on 2017/8/25.
 */

public class DialogFragment extends AppCompatDialogFragment {

    DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 取出 Bundle 对象中保存的数据
        Date date = (Date) getArguments().getSerializable("date");

        // 创建日期对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 解析得到年月日
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建布局对象
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_picker, null);

        // 获取 Dialog 组件对象
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);

        // 初始化 Dialog 组件
        mDatePicker.init(year,month,day,null);

        // 创建对话框视图
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("设置日期")
                //.setPositiveButton("确定",null)
             .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     int year = mDatePicker.getYear();
                     int month = mDatePicker.getMonth();
                     int day = mDatePicker.getDayOfMonth();

                     Date date = new GregorianCalendar(year,month,day).getTime();
                     sendResult(Activity.RESULT_OK,date);
                 }
             })
                .create();
    }


    // 创建静态方法包装 fragment 接受外部传入的 date 数据
    public static DialogFragment newInstance(Date date){
        Bundle bundle = new Bundle();
        bundle.putSerializable("date",date);

        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void sendResult(int resultCode ,Date date){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("data",date);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);

    }


}
