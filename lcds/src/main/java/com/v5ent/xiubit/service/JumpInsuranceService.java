package com.v5ent.xiubit.service;

import android.content.Context;
import android.content.Intent;

import com.toobei.common.entity.GotoInsuranceProductDetailEntiy;
import com.toobei.common.entity.GotoPersonInsureEntiy;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.v5ent.xiubit.activity.LoginActivity;
import com.v5ent.xiubit.activity.ThirdOrgWebActivity;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/13
 */

public class JumpInsuranceService {
    public static final String TAG_PRODUCT_DETIAL = "1";
    public static final String TAG_USER_CENTER = "2";
    private String tag;
    private Context ctx;
    private String caseCode;

    public JumpInsuranceService(Context ctx, String caseCode , String tag) {
        this.ctx = ctx;
        this.caseCode = caseCode;
        this.tag = tag;
    }

    public void run() {
        if (! LoginService.getInstance().isTokenExsit()){
            ctx.startActivity(new Intent(ctx, LoginActivity.class));
            return;
        }
        switch (tag){
            case TAG_PRODUCT_DETIAL:
                jumpProductDetial();
                break;
            case TAG_USER_CENTER:
                jumpUserCenter();
                break;
        }
    }

    private void jumpUserCenter() {
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .gotoPersonInsure(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<GotoPersonInsureEntiy>() {
                    @Override
                    public void bindViewWithDate(GotoPersonInsureEntiy response) {
                        String url =  response.getData().requestUrl + "?"
                                +"hidenav=" + response.getData().hidenav + "&"
                                +"partnerId=" + response.getData().partnerId + "&"
                                +"paySuccess=" + response.getData().paySuccess + "&"
                                +"sign=" + response.getData().sign + "&"
                                +"toLogin=" + response.getData().toLogin + "&"
                                +"hidenav=" + response.getData().hidenav + "&"
                                +"platform=" + response.getData().platform + "&"
                                +"subPartnerId=" + response.getData().subPartnerId + "&"
                                +"partnerUniqKey=" + response.getData().partnerUniqKey;
                        ThirdOrgWebActivity.showThis(ctx,url,true,"慧择",TAG_USER_CENTER);
                    }
                });
    }

    private void jumpProductDetial() {
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .gotoInsuranceProductDetail(new ParamesHelp()
                        .put("caseCode", caseCode)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<GotoInsuranceProductDetailEntiy>() {
                    @Override
                    public void bindViewWithDate(GotoInsuranceProductDetailEntiy response) {
                        String url =  response.getData().requestUrl + "?"
                                +"caseCode=" + response.getData().caseCode + "&"
                                +"partnerId=" + response.getData().partnerId + "&"
                                +"paySuccess=" + response.getData().paySuccess + "&"
                                +"sign=" + response.getData().sign + "&"
                                +"toLogin=" + response.getData().toLogin + "&"
                                +"hidenav=" + response.getData().hidenav + "&"
                                +"platform=" + response.getData().platform + "&"
                                +"subPartnerId=" + response.getData().subPartnerId + "&"
                                +"partnerUniqKey=" + response.getData().partnerUniqKey;
                        ThirdOrgWebActivity.showThis(ctx,url,true,"慧择",TAG_PRODUCT_DETIAL);
                    }
                });
    }
}
