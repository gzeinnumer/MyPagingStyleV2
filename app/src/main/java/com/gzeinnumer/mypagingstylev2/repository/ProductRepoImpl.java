package com.gzeinnumer.mypagingstylev2.repository;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.lifecycle.MutableLiveData;

import com.gzeinnumer.mypagingstylev2.base.BaseConstant;
import com.gzeinnumer.mypagingstylev2.base.BaseListResponse;
import com.gzeinnumer.mypagingstylev2.base.BaseResource;
import com.gzeinnumer.mypagingstylev2.data.ApiService;
import com.gzeinnumer.mypagingstylev2.data.RetroServer;
import com.gzeinnumer.mypagingstylev2.helper.GblFunction;
import com.gzeinnumer.mypagingstylev2.model.ProductResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

public class ProductRepoImpl implements ProductRepo {

//    private final ProductRepoImpl repo;
//    repo = new ProductRepoImpl(application.getApplicationContext());

    private final Context context;
    private final CompositeDisposable compositeDisposable;
    private final ConnectivityManager cm;
    private final ApiService apiService;

    public ProductRepoImpl(Context applicationContext) {
        this.context = applicationContext;
        this.compositeDisposable = new CompositeDisposable();
        this.cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.apiService = RetroServer.getInstance();
        this.product = new MutableLiveData<>();
    }

    private boolean isConnect() {
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private MutableLiveData<BaseResource<List<ProductResponse>>> product;

    public void setProduct(int page, String start_date, String end_date) {
        product.postValue(BaseResource.loading());

        if (isConnect()){
            compositeDisposable.add(
                    apiService.getProduct(BaseConstant.PERPAGE, page, start_date, end_date)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                int code = response.code();
                                BaseListResponse<ProductResponse> res = response.body();
                                if (res.getStatus().equals(BaseConstant.RES_SUCCESS)) {
                                    List<ProductResponse> list = response.body().getData();
                                    product.postValue(BaseResource.success(res.getTitle(), res.getMessage(), list, res.getInfo().getTotal()));
                                } else if(res.getStatus().equals(BaseConstant.RES_TC)) {
                                    product.postValue(BaseResource.error(res.getTitle(), BaseConstant.RES_TC_MSG));
                                } else {
                                    String msg = GblFunction.msgDebugOrRelease(response.toString()+"\n\n"+res.getMessage(), res.getMessage());
                                    product.postValue(BaseResource.error(res.getTitle(), msg));
                                }
                            }, throwable -> {
                                String msg = GblFunction.msgDebugOrRelease(throwable.getMessage(), BaseConstant.RES_TC_MSG);
                                product.postValue(BaseResource.error(BaseConstant.RES_TC_MSG_TITLE,msg));
                            })
            );
        } else {
            product.postValue(BaseResource.error("Error", BaseConstant.RES_TC_MSG));
        }
    }

    public MutableLiveData<BaseResource<List<ProductResponse>>> getProduct() {
        return product;
    }
}