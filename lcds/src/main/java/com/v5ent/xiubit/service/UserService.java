package com.v5ent.xiubit.service;

import com.toobei.common.entity.UserInfo;
import com.toobei.common.entity.UserLevelInfoEntity;
import com.toobei.common.service.TopUserService;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.entity.CustomerListDatasDataEntity;

import org.xsl781.db.assit.QueryBuilder;

import java.util.List;

public class UserService extends TopUserService {

    private static UserService service = null;

    protected UserService() {
        super();
        service = this;
    }

    public static synchronized UserService getInstance() {
        if (service == null) {
            service = new UserService();
        }
        return service;
    }

    /**
     * 功能：初始化所有客户到本地
     *
     * @throws Exception
     */
    public void getAllCustomerDataFromServer(boolean isCache) throws Exception {
        CustomerListDatasDataEntity entity = MyApp.getInstance().getHttpService().customerGetMyCustomers(MyApp.getInstance().getLoginService().token, null, null, 1 + "", "50", null, null);
        if (entity != null && entity.getData() != null && entity.getData().getDatas() != null) {
            UserService.getInstance().saveCustoms(entity.getData().getDatas(), isCache);

            for (int i = 2; i <= entity.getData().getPageCount(); i++) {
                entity = MyApp.getInstance().getHttpService().customerGetMyCustomers(MyApp.getInstance().getLoginService().token, null, null, i + "", "50", null, null);
                if (entity != null && entity.getData() != null && entity.getData().getDatas() != null) {
                    UserService.getInstance().saveCustoms(entity.getData().getDatas(), isCache);
                }
            }
        }
        MyApp.getInstance().getCurUserSp().setCustomLoaded(true);
    }

    /**
     * 功能：得到数据库userInfo表中全部的mobile字段
     *
     * @return
     */
    public List<UserInfo> getAllCustomerPhones() {
        QueryBuilder qb = new QueryBuilder(UserInfo.class);
        qb.columns(new String[]{"mobile"});
        //	qb.where(" 1 =  ? ", new String[] { "1" });
        return MyApp.getInstance().getCurUserDB().query(qb);
    }

    /**
     * 功能：用于获取当前用户的信息
     *
     * @return
     */
    @Override
    public UserInfo getCurUserByTokenFromMyServer() {
        UserLevelInfoEntity entity = null;
        try {
            entity = MyApp.getInstance().getHttpService().userLevelInfo(MyApp.getInstance().getLoginService().token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity != null && entity.getData() != null) {
            return entity.getData().toUserInfo();
        }
        return null;
    }

}
