package com.gzeinnumer.mypagingstylev2.ui.productV2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.akiniyalocts.pagingrecycler.PagingDelegate;
import com.google.android.material.tabs.TabLayout;
import com.gzeinnumer.mypagingstylev2.R;
import com.gzeinnumer.mypagingstylev2.adapterPaging.ProductPager;
import com.gzeinnumer.mypagingstylev2.base.BaseActivity;
import com.gzeinnumer.mypagingstylev2.databinding.ActivityProductV1Binding;
import com.gzeinnumer.mypagingstylev2.databinding.ActivityProductV2Binding;
import com.gzeinnumer.mypagingstylev2.model.PagingParams;
import com.gzeinnumer.mypagingstylev2.model.ProductResponse;
import com.gzeinnumer.mypagingstylev2.ui.productFilter.ProductFilterDialog;
import com.gzeinnumer.mypagingstylev2.ui.productV1.ProductVMV1;

import java.util.ArrayList;
import java.util.List;

//Paging_V2_No_TabLayout
public class ProductActivityV2 extends BaseActivity {

    private ActivityProductV2Binding binding;
    private ProductVMV1 vm;
    private PagingParams params = new PagingParams();
    private List<ProductResponse> list = new ArrayList<>();
    private String start_date = "";
    private String end_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductV2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(ProductVMV1.class);

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {
        initPaging();
    }

    private void initTextWatcher() {

    }

    private void initObserver() {
        vm.getProduct().observe(this, resource -> {
            switch (resource.status) {
                case STATUS_1_SUCCESS:
                    swipe(binding.swipeRefreshLayout, false);
                    list = resource.data;
                    emptyState(list != null ? list.size() : 0, binding.rv, binding.imgEmpty);
                    params.setTotalData(resource.total);
                    adapter.addNewItem(resource.data);
                    break;
                case STATUS_2_ERROR:
                    swipe(binding.swipeRefreshLayout, false);
                    emptyState(list != null ? list.size() : 0, binding.rv, binding.imgEmpty);
                    break;
                case STATUS_6_LOADING:
                    swipe(binding.swipeRefreshLayout, true);
                    emptyState(list != null ? list.size() : 0, binding.rv, binding.imgEmpty);
                    break;
            }
        });
    }

    private void initOnClick() {
        debugLocationActivity(binding.tvToolbar, getClass().getSimpleName());
        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.btnPopup.setOnClickListener(view -> showFilterDialog());
        binding.swipeRefreshLayout.setOnRefreshListener(this::initPaging);

        binding.imgClearFilter.setOnClickListener(v -> {
            start_date = "";
            end_date = "";
            binding.tvFilterText.setText("");
            binding.llFilterText.setVisibility(View.GONE);
            initPaging();
        });
    }

    protected void initPaging() {
        params = new PagingParams();
        initProductPager();
        vm.setProduct(1,start_date,end_date);
    }

    private ProductPager adapter;

    private void initProductPager() {
        list = new ArrayList<>();
        adapter = new ProductPager();
        adapter.setCallBack((type, position, data) -> {
            switch (type) {
                case 1:
                    Toast.makeText(getApplicationContext(), "Intent to detail "+data.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
            }
        });

        adapter.setBaseDebugCallback((type, position, data) -> {
            debugDialog(data.toString());
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rv.hasFixedSize();

        new PagingDelegate.Builder(adapter)
                .attachTo(binding.rv)
                .listenWith(new PagingDelegate.OnPageListener() {
                    @Override
                    public void onPage(int i) {
                        if (params.getCurrentPage() < params.getTotalPage()) {
                            params.addCurrentPage();
                            vm.setProduct(params.getCurrentPage(), start_date,end_date);
                        } else {
                            onDonePaging();
                        }
                    }

                    @Override
                    public void onDonePaging() {

                    }
                })
                .build();
    }


    private void showFilterDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag(ProductFilterDialog.TAG);
        if (previous != null) {
            transaction.remove(previous);
        }
        ProductFilterDialog dialog = ProductFilterDialog.newInstance(start_date, end_date, (start_date, end_date) -> {
            this.start_date = start_date;
            this.end_date = end_date;

            if (start_date.length()>0 || end_date.length()>0){
                binding.tvFilterText.setText(start_date+" sd "+end_date);
                binding.llFilterText.setVisibility(View.VISIBLE);
            } else {
                binding.tvFilterText.setText("");
                binding.llFilterText.setVisibility(View.GONE);
            }
            initPaging();
        });
        dialog.show(transaction, ProductFilterDialog.TAG);
    }
}