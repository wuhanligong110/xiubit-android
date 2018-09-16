package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.BitmapUtil;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.ScreenShotUtils;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recylerapter.GoodReportAdapter;
import com.v5ent.xiubit.entity.GoodTransData;
import com.v5ent.xiubit.entity.GoodTransEntity;
import com.v5ent.xiubit.view.popupwindow.OneImageSharePopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.MD5Util;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/27
 */

public class GoodReportActivity extends MyNetworkBaseActivity<GoodTransEntity> {

    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.introduceTv)
    TextView introduceTv;
    @BindView(R.id.moneyTv)
    TextView moneyTv;
    @BindView(R.id.allReportTv)
    TextView allReportTv;
    @BindView(R.id.oneKeyShareTv)
    TextView oneKeyShareTv;
    @BindView(R.id.notEmptyBottomLl)
    LinearLayout notEmptyBottomLl;
    @BindView(R.id.inviteFrendTV)
    TextView inviteFrendTV;
    @BindView(R.id.emptyView)
    View emptyView;
    @BindView(R.id.contantV)
    View contantV;
    @BindView(R.id.reportBg)
    ImageView reportBg;
    @BindView(R.id.reportBgbottom)
    ImageView reportBgbottom;
    @BindView(R.id.contantBottomV)
    View contantBottomV;
    @BindView(R.id.topRemindTv)
    View topRemindTv;
    private String billId = ""; //获得最新喜报传"",指定喜报传对应billId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        headerLayout.showTitle("理财喜报");
        headerLayout.showLeftBackButton();
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_good_report;
    }

    @Override
    protected GoodTransEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getGoodTrans(billId);
    }

    @Override
    protected void onRefreshDataView(GoodTransEntity response) {
        GoodTransData bean = response.getData();
        refreshView(bean);

    }

    private void refreshView(GoodTransData bean) {
        if (bean == null) {
            showEmptyView(true);
            return;
        } else {
            showEmptyView(false);
        }
        reportBg.setImageResource(Integer.parseInt(bean.getAmount()) >= 10000 ? R.drawable.good_report_bg_2 : R.drawable.good_report_bg_1);
        reportBgbottom.setImageResource(Integer.parseInt(bean.getAmount()) >= 10000 ? R.drawable.good_report_bg_bottom2 : R.drawable.good_report_bg_bottom1);
        UserInfo curUser = MyApp.getInstance().getLoginService().getCurUser();
        if (curUser == null || TextUtil.isEmpty(curUser.getUserName())) {
            nameTv.setText("您");
        } else {
            if (curUser.getUserName().length() == 11)
                nameTv.setText(curUser.getUserName().replace(curUser.getUserName().substring(3, 7), "****"));
            else {
                String userName = curUser.getUserName();
                nameTv.setText(userName);
            }
        }
        introduceTv.setText("名下" + bean.getUserName() + "用户出单");
        moneyTv.setText(bean.getAmount()+"元");
        TextDecorator.decorate(moneyTv,bean.getAmount()+"元")
                .setAbsoluteSize((int) ctx.getResources().getDimension(R.dimen.w30),"元")
                .build();
//        mDataTv.setText(bean.getInvestTime());
    }



    private void showEmptyView(boolean b) {
        contantV.setVisibility(b ? View.GONE : View.VISIBLE);
        contantBottomV.setVisibility(b ? View.GONE : View.VISIBLE);
        notEmptyBottomLl.setVisibility(b ? View.GONE : View.VISIBLE);
        inviteFrendTV.setVisibility(b ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(b ? View.VISIBLE : View.GONE);
        topRemindTv.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.allReportTv, R.id.oneKeyShareTv, R.id.inviteFrendTV})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.allReportTv:
                showActivity(ctx, AllGoodReportActivity.class);
                break;
            case R.id.oneKeyShareTv:
                shareReport();
                break;
            case R.id.inviteFrendTV:
                intent = new Intent(ctx, InviteCustomerQr.class);
                startActivity(intent);
                break;
        }
    }

    private void shareReport() {
        Bitmap bm = ScreenShotUtils.shot(ctx, contantV);
        String filename = MD5Util.MD5(System.currentTimeMillis() + "");
        File file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, bm, false);
        new OneImageSharePopupWindow(ctx, file.getAbsolutePath()).showPopupWindow(contantV);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 选择喜报后更新
    public void refreshReport(GoodTransData event) {
        billId = event.getBillId();
        loadData(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GoodReportAdapter.currentSelectId = null;
    }
}
