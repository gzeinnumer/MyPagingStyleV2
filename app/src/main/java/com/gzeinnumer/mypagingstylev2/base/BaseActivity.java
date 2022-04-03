package com.gzeinnumer.mypagingstylev2.base;

import static com.gzeinnumer.mypagingstylev2.helper.GblFunction.isDebugActive;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gzeinnumer.da.dialog.debug.DebugDialog;
import com.gzeinnumer.mypagingstylev2.BuildConfig;
import com.gzeinnumer.mypagingstylev2.R;

public class BaseActivity extends AppCompatActivity {

    protected void debugLocationActivity(View v, String TAG) {
        if (BuildConfig.DEBUG) {
            v.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), TAG, Toast.LENGTH_SHORT).show();
            });
        }
    }

//    gzn_paging_v2_base_activity
    protected void swipe(SwipeRefreshLayout swipeRefreshLayout, boolean swipe) {
        swipeRefreshLayout.setRefreshing(swipe);
    }

    protected void emptyState(int size, RecyclerView rv, LinearLayout imgEmpty) {
        rv.setVisibility(View.GONE);
        imgEmpty.setVisibility(View.GONE);

        rv.setVisibility(size == 0 ? View.GONE : View.VISIBLE);
        imgEmpty.setVisibility(size == 0 ? View.VISIBLE : View.GONE);
    }

    protected void debugDialog(String smg) {
        if (isDebugActive())
            new DebugDialog(getSupportFragmentManager())
                    .setAnimationStyle(R.style.CustomDialogStyle)
                    .setContent(smg)
                    .onOkPressedCallBack(() -> {
                        //ok action
                    })
                    .show();
    }
}
