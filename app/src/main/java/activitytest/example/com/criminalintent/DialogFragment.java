package activitytest.example.com.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by pan on 2017/8/25.
 */

public class DialogFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_picker, null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("设置日期")
                .setPositiveButton("退出",null)
                .create();
    }
}
