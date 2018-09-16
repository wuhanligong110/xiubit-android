package com.v5ent.xiubit.service;

import android.content.Intent;

import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.toobei.common.view.dialog.PromptDialogCommon;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.CardManagerAdd;
import com.v5ent.xiubit.activity.LoginActivity;
import com.v5ent.xiubit.activity.ThirdOrgWebActivity;
import com.toobei.common.entity.FundAccountEntity;
import com.toobei.common.entity.FundAccountPageEntity;
import com.toobei.common.entity.FundDetialPageEntity;
import com.toobei.common.entity.FundHomePageEntity;
import com.toobei.common.entity.FundRegisterStatueEntity;
import com.toobei.common.network.NetworkObserver;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc
 * 类说明: 用于基金跳转流程
 *
 * @author yangLin
 * @time 2017/8/17
 */

public class JumpFundService {

    private String jumpType;
    public static final String JUMP_TYPE_PRODUCT_DETIAL = "JUMP_TYPE_PRODUCT_DETIAL";   //跳转到基金详情页
    public static final String JUMP_TYPE_USER_CENTER = "JUMP_TYPE_USER_CENTER";  //跳转到基金个人资产页
    public static final String JUMP_TYPE_HOMEPAGE = "JUMP_TYPE_HOMEPAGE";  //跳转到第三方用户中心
    private TopBaseActivity ctx;
    private String productCode;

    public JumpFundService(String jumpType, TopBaseActivity activity, String productCode) {
        this.jumpType = jumpType;
        this.ctx = activity;
        this.productCode = productCode;
    }


    public void jump() throws Exception {
        //检查登陆
        if (!TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) {
            checkBoundCard();
        } else {
            PromptDialogCommon promptDialogCommon = new PromptDialogCommon(ctx, null, "基金详情需登录后才可查看", "立即登录");
            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                @Override
                public void onPositiveClick() { //点击绑定,跳转绑卡页面
                    TopApp.loginAndStay = true;
                    ctx.startActivity(new Intent(ctx, LoginActivity.class));
                }
            });
            promptDialogCommon.show();

        }
    }

    /**
     * 检查绑卡
     */
    private void checkBoundCard() throws Exception {
        MyApp.getInstance().getHttpService().bindCardStatue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BundBankcardDataEntity>() {
                    @Override
                    public void bindViewWithDate(BundBankcardDataEntity response) {
                        if (response.getData().isBundBankcard()) {
                            checkRegistFundStatue();  //检查是否已经注册基金
                        } else {
                            PromptDialogCommon promptDialogCommon = new PromptDialogCommon(ctx, ctx.getString(R.string.dialog_title_boundCard_remind), ctx.getString(R.string.dialog_content_boundCard_remind), "确定");
                            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                                @Override
                                public void onPositiveClick() { //点击绑定,跳转绑卡页面
                                    ctx.startActivity(new Intent(ctx, CardManagerAdd.class));
                                }
                            });
                            promptDialogCommon.show();
                        }
                    }
                });

    }

    /**
     * 查询用户是否注册奕丰基金
     */
    private void checkRegistFundStatue() {
        MyApp.getInstance().getHttpService().fundRegisterStatue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundRegisterStatueEntity>() {
                    @Override
                    public void bindViewWithDate(FundRegisterStatueEntity response) {
                        if (response.getData().ifRegister) {
                            //跳转相应页面
                            skipFundPage();

                        } else {
                            //账户开通提示 
                            PromptDialogCommon promptDialogCommon = new PromptDialogCommon(ctx, "一键开通基金账户？","开通后，将同步个人身份信息和联系方式", "一键开通");
                            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                                @Override
                                public void onPositiveClick() { //点击开通奕丰账户
                                    registFundAccount();
                                }
                            });
                            promptDialogCommon.show();
                        }
                    }
                });
    }

    /**
     * 注册奕丰账户
     */

    private void registFundAccount() {
        MyApp.getInstance().getHttpService().registFund()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundAccountEntity>() {
                    @Override
                    public void bindViewWithDate(FundAccountEntity response) {
                        String accountNumber = response.getData().accountNumber;
                        //跳转相应页面
                        skipFundPage();
                    }
                });
    }

    /**
     * 跳转基金页面
     */
    private void skipFundPage() {
        switch (jumpType) {
            case JUMP_TYPE_HOMEPAGE:
                //跳转基金首页
                RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                        .fundHomePage(new ParamesHelp().build(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundHomePageEntity>() {
                    @Override
                    public void bindViewWithDate(FundHomePageEntity response) {
                       String url =  response.getData().requestUrl + "?"
                                +"integrationMode=" + response.getData().integrationMode + "&"
                                +"referral=" + response.getData().referral + "&"
                                +"data=" + response.getData().data;
                        ThirdOrgWebActivity.showThis(ctx,url,true,"奕丰金融",JUMP_TYPE_HOMEPAGE);
                    }
                });
                break;
            case JUMP_TYPE_PRODUCT_DETIAL:
                // 跳转详情页
                RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                        .fundDetialPage(new ParamesHelp().put("productCode",productCode).build(true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetworkObserver<FundDetialPageEntity>() {
                            @Override
                            public void bindViewWithDate(FundDetialPageEntity response) {
                                String url =  response.getData().requestUrl + "?"
                                        +"integrationMode=" + response.getData().integrationMode + "&"
                                        +"productCode="+ response.getData().productCode + "&"
                                        +"referral=" + response.getData().referral + "&"
                                        +"data=" + response.getData().data;
                                ThirdOrgWebActivity.showThis(ctx,url,true,"奕丰金融",JUMP_TYPE_PRODUCT_DETIAL);
                            }
                        });
                break;
            case JUMP_TYPE_USER_CENTER:
                //跳转个人资产页
                RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                        .fundAccountPage(new ParamesHelp().build(true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetworkObserver<FundAccountPageEntity>() {
                            @Override
                            public void bindViewWithDate(FundAccountPageEntity response) {
                                String url =  response.getData().requestUrl + "?"
                                        +"integrationMode=" + response.getData().integrationMode + "&"
                                        +"referral=" + response.getData().referral + "&"
                                        +"data=" + response.getData().data;
                                ThirdOrgWebActivity.showThis(ctx,url,true,"奕丰金融",JUMP_TYPE_USER_CENTER);
                            }
                        });
                break;
        }
    }

    

}
