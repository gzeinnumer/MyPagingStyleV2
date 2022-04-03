package com.gzeinnumer.mypagingstylev2.ui.productFilter;

import static com.gzeinnumer.mypagingstylev2.helper.GblFunction.getDateIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.gzeinnumer.mypagingstylev2.base.BaseFilterDialogFragment;
import com.gzeinnumer.mypagingstylev2.databinding.DialogProductFilterBinding;
import com.gzeinnumer.mypagingstylev2.helper.GblFunction;

//FilterDialog
public class ProductFilterDialog extends BaseFilterDialogFragment {

    private final CallBack callBack;
    private DialogProductFilterBinding binding;
    private ProductFilterVM vm;
    private final String start_date;
    private final String end_date;

    public ProductFilterDialog(String start_date, String end_date, CallBack callBack) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.callBack = callBack;
    }

    public static ProductFilterDialog newInstance(String start_date, String end_date, CallBack callBac) {
        return new ProductFilterDialog(start_date, end_date, callBac);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DialogProductFilterBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ProductFilterVM.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {
        binding.edStartDate.setText(start_date);
        binding.edEndDate.setText(end_date);
    }

    private void initTextWatcher() {

    }

    private void initObserver() {
    }

    private void initOnClick() {
        binding.edStartDate.setOnClickListener(view -> {
            showDialogDateRange();
        });
        binding.edEndDate.setOnClickListener(view -> {
            showDialogDateRange();
        });
        binding.btnPilih.setOnClickListener(view -> {
            String fromDate = getDateIn(-90);
            String toDate = getDateIn(90);
            String toCheck = binding.edStartDate.getText().toString();
            if (toCheck.length() > 0) {
                boolean isInRange = GblFunction.checkBetween(toCheck, fromDate, toDate);

                if (isInRange) {
                    callBack.dateRange(binding.edStartDate.getText().toString(), binding.edEndDate.getText().toString());
                    getDialog().dismiss();
                } else {
                    onShowCustomToast("Tidak boleh melebihi 90 hari");
                }
            } else {
                callBack.dateRange(binding.edStartDate.getText().toString(), binding.edEndDate.getText().toString());
                getDialog().dismiss();
            }
        });
        binding.btnPilihUlang.setOnClickListener(view -> {
            pilihUlangAction();
        });
    }

    private void showDialogDateRange() {
        datePickerMultiChild().onOkPressedCallBack((firstDate, secondDate) -> {
            binding.edStartDate.setText(firstDate);
            binding.edEndDate.setText(secondDate);
        }).build().show();
    }

    private void pilihUlangAction() {
        binding.edStartDate.setText("");
        binding.edEndDate.setText("");
    }

    public interface CallBack {
        void dateRange(String start_date, String end_date);
    }
}