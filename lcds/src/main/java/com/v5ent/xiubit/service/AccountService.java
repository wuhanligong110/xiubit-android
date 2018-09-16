package com.v5ent.xiubit.service;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.service.TopAccountService;
import com.v5ent.xiubit.activity.CardManagerAdd;
import com.v5ent.xiubit.activity.PwdManagerInitPay;

public class AccountService extends TopAccountService {
    private static AccountService service = null;

    private AccountService() {

    }

    public static synchronized AccountService getInstance() {
        if (service == null) {
            service = new AccountService();
        }
        return service;
    }

    @Override
    public void showCardManagerAdd(TopBaseActivity activity) {
        activity.showActivity(activity, CardManagerAdd.class);
    }

    @Override
    public void showPwdManagerInitPay(TopBaseActivity activity) {
        //跳转到初始化交易 密码
        activity.showActivity(activity, PwdManagerInitPay.class);
    }
}
