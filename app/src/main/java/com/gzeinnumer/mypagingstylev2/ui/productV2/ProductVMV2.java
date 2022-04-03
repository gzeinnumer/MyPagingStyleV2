package com.gzeinnumer.mypagingstylev2.ui.productV2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gzeinnumer.mypagingstylev2.base.BaseResource;
import com.gzeinnumer.mypagingstylev2.model.ProductResponse;
import com.gzeinnumer.mypagingstylev2.repository.ProductRepoImpl;

import java.util.List;

public class ProductVMV2 extends AndroidViewModel {

    private final ProductRepoImpl repoProduct;

    public ProductVMV2(Application application) {
        super(application);
        repoProduct = new ProductRepoImpl(application.getApplicationContext());
        product = repoProduct.getProduct();
    }

    private final LiveData<BaseResource<List<ProductResponse>>> product;

    public void setProduct(int page, String start_date, String end_date) {
        repoProduct.setProduct(page, start_date, end_date);
    }

    public LiveData<BaseResource<List<ProductResponse>>> getProduct() {
        return product;
    }
}