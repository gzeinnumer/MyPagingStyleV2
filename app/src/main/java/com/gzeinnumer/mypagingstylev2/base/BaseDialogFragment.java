package com.gzeinnumer.mypagingstylev2.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gzeinnumer.da.dialog.confirmDialog.ConfirmDialog;
import com.gzeinnumer.da.dialog.datePickerDialog.multi.MultiDatePickerDialog;
import com.gzeinnumer.da.dialog.datePickerDialog.single.SingleDatePickerDialog;
import com.gzeinnumer.da.dialog.debug.DebugDialog;
import com.gzeinnumer.da.dialog.infoDialog.InfoDialog;
import com.gzeinnumer.da.dialog.loadingDialog.LoadingDialog;
import com.gzeinnumer.edf.MyLibDialog;
import com.gzeinnumer.mypagingstylev2.R;
import com.gzeinnumer.mypagingstylev2.helper.GblFunction;

public class BaseDialogFragment extends MyLibDialog {
    private LoadingDialog loadingDialog;

    public BaseDialogFragment() {
        super(R.style.CustomDialogStyle);
    }

    public BaseDialogFragment(int style) {
        super(style);
    }

    protected void intentTo(Class<?> clss) {
        startActivity(new Intent(requireContext(), clss));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            customType(requireContext(), BaseConstant.INTENT_ANIM_TYPE);
        }
    }

    protected void intentToPut(Intent intent) {
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            customType(requireContext(), BaseConstant.INTENT_ANIM_TYPE);
        }
    }
    protected void onShowCustomToast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected MultiDatePickerDialog datePickerMultiChild() {
        return new MultiDatePickerDialog(getChildFragmentManager())
                .setTimeZone("GMT")
                .setTitle("Pilih tanggal")
                .setTimeFormat("yyyy-MM-dd");
    }
}
