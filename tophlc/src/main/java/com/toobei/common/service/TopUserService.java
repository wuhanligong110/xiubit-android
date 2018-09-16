package com.toobei.common.service;

import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.entity.Custom;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.entity.UserInfoDatasDataEntity;
import com.toobei.common.utils.NetAsyncTaskCallBack;
import com.toobei.common.utils.UpdateViewCallBack;

import org.xsl781.db.assit.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TopUserService {

    protected static Map<String, UserInfo> usersCache = new HashMap<String, UserInfo>();
    private String callServiceEMId;

    /**
     * 不可以在UI现场调用 先从内存，或查数据库，最后从服务器拉取数据
     */
    public UserInfo getCurUser() {
        if (TopApp.getInstance().getLoginService().getCurUser() == null) {
            QueryBuilder qb = new QueryBuilder(UserInfo.class);
            qb.where("userId = ?", new String[]{TopApp.getInstance().getLoginService().getCurUserId()});
            List<UserInfo> entitys = TopApp.getInstance().getCurUserDB().query(qb);
            if (entitys != null && entitys.size() > 0 && entitys.get(0).getEasemobAcct().length() > 0) {
                TopApp.getInstance().getLoginService().setCurUser(entitys.get(0));
            } else {
                TopApp.getInstance().getLoginService().setCurUser(getCurUserByTokenFromMyServer());
            }
        }
        return TopApp.getInstance().getLoginService().getCurUser();
    }

    public void getCurUser(final UpdateViewCallBack<UserInfo> callBack) {
        new NetAsyncTaskCallBack<UserInfo>(callBack) {

            @Override
            protected UserInfo doInBack() throws Exception {
                return getCurUser();
            }
        }.execute();
    }

    /**
     * 不可以在UI现场调用 先从内存，或查数据库，最后从服务器拉取数据
     */
    public UserInfo getUserByEmIdAndSaveCache(String emId) {
        if (emId == null) return null;
        UserInfo entity = lookupUser(emId);
        if (entity == null) {
            entity = TopApp.getInstance().getCurUserDB().queryById(emId, UserInfo.class);
            if (entity == null) {
                entity = getUserFromMyServerAndSaveCache(emId);
            } else {
                registerUserCache(entity);
            }
        }
        return entity;
    }

    /**
     * 功能：从本地取用户数据，如果没有开线程去服务器取
     *
     * @param emId
     * @return
     */
    public UserInfo getUserByEmIdFromLocal(final String emId) {
        if (emId == null) return null;
        UserInfo entity = lookupUser(emId);
        if (entity == null) {
            entity = TopApp.getInstance().getCurUserDB().queryById(emId, UserInfo.class);
            if (entity == null) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        getUserFromMyServerAndSaveCache(emId);
                    }
                }).start();
            } else {
                registerUserCache(entity);
            }
        }
        return entity;
    }

    /**
     * 功能：异步拿到数据，通过回调传出数据
     */
    public void getUserByEmIdAndSaveCache(final String emId, final UpdateViewCallBack<UserInfo> callBack) {
        new NetAsyncTaskCallBack<UserInfo>(callBack) {

            @Override
            protected UserInfo doInBack() throws Exception {
                return getUserByEmIdAndSaveCache(emId);
            }
        }.execute();
    }

    public UserInfo lookupUser(String emId) {
        return usersCache.get(emId);
    }

    public void registerUserCache(UserInfo user) {
        if (user == null) return;
        registerUserCache(user.getEasemobAcct(), user);
    }

    public void registerBatchUserCache(List<UserInfo> users) {
        for (UserInfo user : users) {
            registerUserCache(user);
        }
    }

    public UserInfo getUserFromMyServerAndSaveCache(String emId) {
        UserInfo user = getUserFromByEmIdMyServer(emId);
        if (user != null) {
            saveAndCache(user);
        }
        return user;
    }

    public void saveAndCache(UserInfo userInfo) {
        if (userInfo != null && userInfo.getEasemobAcct().length() > 0) {
            registerUserCache(userInfo);
            TopApp.getInstance().getCurUserDB().save(userInfo);
        }
    }

    public void saveCustoms(List<Custom> customs, boolean isCache) {
        if (customs != null && customs.size() > 0) {
            // 拿到客户数据后，在本地进行缓存
            for (Custom custom : customs) {
                if (custom.getEasemobAcct().length() > 0) {
                    UserInfo user = custom.toUserInfo("false");
                    TopApp.getInstance().getCurUserDB().save(user);
                    if (isCache) {
                        registerUserCache(user);
                    }
                }
            }
        }
    }

    /**
     * 功能：用环信id查用户信息
     *
     * @return
     */
    private UserInfo getUserFromByEmIdMyServer(String emId) {
        UserInfoDatasDataEntity entitys = null;
        try {
            entitys = TopApp.getInstance().getHttpService().userInfoByEmId(TopApp.getInstance().getLoginService().token, emId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entitys != null && entitys.getData() != null && entitys.getData().getDatas() != null && entitys.getData().getDatas().size() > 0) {
            return entitys.getData().getDatas().get(0);
        }
        return null;
    }

    /**
     * 功能：用于获取当前用户的信息
     *
     * @return
     */
    public abstract UserInfo getCurUserByTokenFromMyServer();

    private void registerUserCache(String emId, UserInfo user) {
        usersCache.put(emId, user);
    }

    public String getCallServiceEMId() {
        if (callServiceEMId == null) {
            callServiceEMId = TopApp.getInstance().getDefaultSp().getCallServiceEMId();
        }
        return callServiceEMId;
    }

    /**
     * 功能：判断当前用户是否为客服人员
     *
     * @param chatUserId
     * @return
     */
    public boolean isCallServiceUser(String chatUserId) {
        return chatUserId != null && chatUserId.equals(getCallServiceEMId());
    }

    private DisplayImageOptions getUserFaceImageOptions(boolean isCustomer) {
        int defaultImageResource = R.drawable.img_mine_cfp_face;
        if (isCustomer) {
            defaultImageResource = R.drawable.img_im_customer_face;
        }
        DisplayImageOptions userFaceImageOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(defaultImageResource).showImageOnLoading(defaultImageResource).showImageOnFail(defaultImageResource).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Config.ALPHA_8).resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
                //.displayer(new RoundedBitmapDisplayer(20))
                //.displayer(new FadeInBitmapDisplayer(300))// 淡入
                .build();
        return userFaceImageOptions;
    }

    /**
     * 功能：
     *
     * @param url
     * @param imageView
     * @param isCustomer true是客户，falce为理财师
     */
    public void displayUserFace(String url, ImageView imageView, boolean isCustomer) {
        if (imageView == null) {
            return;
        }
        ImageLoader.getInstance().displayImage(url, imageView, getUserFaceImageOptions(isCustomer));
    }
}
