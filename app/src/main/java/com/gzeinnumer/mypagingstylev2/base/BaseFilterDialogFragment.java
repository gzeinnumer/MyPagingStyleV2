package com.gzeinnumer.mypagingstylev2.base;

import android.view.Gravity;

import com.gzeinnumer.mypagingstylev2.R;

public class BaseFilterDialogFragment extends BaseDialogFragment {
    public BaseFilterDialogFragment() {
        super();
    }

    public BaseFilterDialogFragment(int style) {
        super(R.style.CustomDialogStyleFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        setGravity(Gravity.BOTTOM);
        setCanvasWidth(0.95);
    }
}
