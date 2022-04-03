package com.gzeinnumer.mypagingstylev2.ui.productV1;

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
import com.gzeinnumer.mypagingstylev2.adapterPaging.ProductPager;
import com.gzeinnumer.mypagingstylev2.base.BaseActivity;
import com.gzeinnumer.mypagingstylev2.databinding.ActivityProductV1Binding;
import com.gzeinnumer.mypagingstylev2.model.PagingParams;
import com.gzeinnumer.mypagingstylev2.model.ProductResponse;
import com.gzeinnumer.mypagingstylev2.ui.productFilter.ProductFilterDialog;

import java.util.ArrayList;
import java.util.List;

//Paging_V2_TabLayout
public class ProductActivityV1 extends BaseActivity {

    private ActivityProductV1Binding binding;
    private ProductVMV1 vm;
    private PagingParams params = new PagingParams();
    private List<ProductResponse> listProduct = new ArrayList<>();
    private String start_date = "";
    private String end_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductV1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(ProductVMV1.class);

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {
        initPaging();
        initViewPager();
    }

    private void initTextWatcher() {

    }

    private void initObserver() {
        vm.getProduct().observe(this, resource -> {
            switch (resource.status) {
                case STATUS_1_SUCCESS:
                    swipe(binding.swipeRefreshLayout, false);
                    listProduct = resource.data;
                    emptyState(listProduct != null ? listProduct.size() : 0, binding.rv, binding.imgEmpty);
                    params.setTotalData(resource.total);
                    adapter.addNewItem(resource.data);
                    break;
                case STATUS_2_ERROR:
                    swipe(binding.swipeRefreshLayout, false);
                    emptyState(listProduct != null ? listProduct.size() : 0, binding.rv, binding.imgEmpty);
                    break;
                case STATUS_6_LOADING:
                    swipe(binding.swipeRefreshLayout, true);
                    emptyState(listProduct != null ? listProduct.size() : 0, binding.rv, binding.imgEmpty);
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
        listProduct = new ArrayList<>();
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
        ProductFilterDialog dialog = ProductFilterDialog.newInstance(start_date, end_date, (firstDate, lastDate) -> {
            this.start_date = firstDate;
            this.end_date = lastDate;

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

    private void initViewPager() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<TabLayout.Tab> tabItems = new ArrayList<>();
                tabItems.add(binding.tabLayout.getTabAt(0));
                tabItems.add(binding.tabLayout.getTabAt(1));
                for (int i = 0; i < tabItems.size(); i++) {
                    tabItems.get(i).view.setEnabled(false);
                }

                switch (tab.getPosition()) {
                    case 0:
                        start_date = "";
                        end_date = "";
                        initPaging();
                        break;
                    case 1:
                        start_date = "2022-05-01";
                        end_date = "2022-05-31";
                        initPaging();
                        break;
                }

                new Handler().postDelayed(() -> {
                    for (int i = 0; i < tabItems.size(); i++) {
                        tabItems.get(i).view.setEnabled(true);
                    }
                }, 1500);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}