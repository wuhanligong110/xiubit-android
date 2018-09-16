package com.toobei.tb.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.MycfpPagerAdapter;
import com.toobei.tb.entity.MycfpDataEntity;
import com.toobei.tb.entity.MycfpInfo;
import com.toobei.tb.view.MyPagerSlidingTabStrip;

import org.xsl781.ui.view.RoundImageView;
import org.xsl781.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;


/**
 * 公司: tophlc
 * 类说明: 我的理财师
 *
 * @author qingyechen
 * @time 2016/12/30 0030 下午 2:48
 */
public class MyCfpActivity extends MyNetworkBaseActivity<MycfpDataEntity> implements View.OnClickListener {


    @BindView(R.id.mycfp_headIV)
    RoundImageView mycfpHeadIV;
    @BindView(R.id.mycfp_nameTV)
    TextView mycfpNameTV;
    @BindView(R.id.cfpLevelName)
    TextView cfpLevelName;
    @BindView(R.id.tabs)
    MyPagerSlidingTabStrip tabs;
    @BindView(R.id.vPager)
    ViewPager vPager;
    private String mobile;
    private String linkUrlKey; //点击通知消息 myCfp_product 跳转我的理财师->理财师推荐产品   myCfp_platform->推荐平台


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkUrlKey = getIntent().getStringExtra("linkUrlKey");
        ButterKnife.bind(this);
        initVIew();
    }

    private void initVIew() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle(getString(R.string.mycfp_activity));
        MycfpPagerAdapter mycfpPagerAdapter = new MycfpPagerAdapter(ctx, getFragManager());
        vPager.setAdapter(mycfpPagerAdapter);
        tabs.setViewPager(vPager);
        if ("myCfp_platform".equals(linkUrlKey)) {
            vPager.setCurrentItem(1);
        }
    }


    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_mycfp;
    }

    @Override
    protected MycfpDataEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().mycfpInfo(MyApp.getInstance().getLoginService().token);

    }

    @Override
    protected void onRefreshDataView(MycfpDataEntity response) {
        MycfpInfo data = response.getData();
        mobile = data.getMobile(); //电话
        if (TextUtils.isEmpty(data.getUserName())) {
            mycfpNameTV.setText("未认证");
        } else {
            mycfpNameTV.setText(data.getUserName());
        }
        cfpLevelName.setText(data.getCfpLevelName());
        //Glide加载图片到RoundImageView　会失败？？
        String headImage = data.getHeadImage();
        if (!TextUtils.isEmpty(headImage)) {
            PhotoUtil.displayUserFace(headImage, mycfpHeadIV);
        }
    }


    @OnClick(R.id.mycfp_call_phoneIV)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mycfp_call_phoneIV:  //打理财师

                callPhone();

                break;
        }
    }


    // TODO: 2017/2/19  android6.0获取打电话权限
    private void callPhone() {
        int hasCallPhonePermission = ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.CALL_PHONE);
        Logger.e("hasReadContact获取打电话权限sPermission===>" + hasCallPhonePermission
        );
        if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(ctx,
                    Manifest.permission.CALL_PHONE)) {

                ActivityCompat.requestPermissions(ctx,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_DIAL);

            }
            Logger.e("hasReadContact获取打电话权限sPermission===2222222>" + hasCallPhonePermission);
            ActivityCompat.requestPermissions(ctx,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_DIAL);
            return;
        } else {
            PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, getString(R.string.mycfp_dial), mobile);
            dialog.setBtnPositiveColor(R.color.text_red_common);
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_DIAL:

                Logger.e(permissions.toString());
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, getString(R.string.mycfp_dial), mobile);
                    dialog.setBtnPositiveColor(R.color.text_red_common);
                    dialog.show();
                    Logger.e("user granted the permission!");

                } else {
                    Logger.e("user denied the permission!");
                }
                break;
        }

        return;

    }
}
