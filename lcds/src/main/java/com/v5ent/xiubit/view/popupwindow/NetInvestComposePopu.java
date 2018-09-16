package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.InvestedPlatformData;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.service.ShareService;
import com.toobei.common.utils.BitmapUtil;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.ScreenShotUtils;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.RCRelativeLayout;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.InviteCfpOutCreateQrData;
import com.v5ent.xiubit.network.httpapi.QrCodeApi;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;
import com.v5ent.xiubit.view.OrgListChartPieLayout;

import org.xsl781.utils.Logger;
import org.xsl781.utils.MD5Util;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hasee-pc on 2017/12/4.
 */

public class NetInvestComposePopu extends PopupWindow {

    @BindView(R.id.cardView)
    RCRelativeLayout cardView;
    @BindView(R.id.text_share_name)
    TextView textShareName;
    private InvestedPlatformData data;
    @BindView(R.id.btn_save_to_photo_album)
    ImageView btnSaveToPhotoAlbum;
    @BindView(R.id.btn_share_qq)
    ImageView btnShareQq;
    @BindView(R.id.btn_wechat_friend)
    ImageView btnWechatFriend;
    @BindView(R.id.btn_wechat_cricle)
    ImageView btnWechatCricle;
    @BindView(R.id.orgNumInfoTv)
    TextView orgNumInfoTv;
    @BindView(R.id.orgYearProfitTv)
    TextView orgYearProfitTv;
    @BindView(R.id.chartPieCp)
    OrgListChartPieLayout chartPieCp;
    @BindView(R.id.logoIv)
    ImageView logoIv;


    private String imagePath;
    private View contentView;
    protected TopBaseActivity ctx;
    private ShareService shareService;


    public NetInvestComposePopu(Context ctx, InvestedPlatformData data) {
        super(ctx);
        this.ctx = (TopBaseActivity) ctx;
        this.data = data;
        shareService = new ShareService();
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_net_invest_compose_popu, null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx, R.color.Color_0));
        setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.anim_popup_share);

        initData();

    }

    private void initData() {
        if (data == null) return;
        TextDecorator.decorate(orgNumInfoTv, "在投平台：" + data.investingPlatformNum + " 个")
                .setTextColor(R.color.text_red_common, "" + data.investingPlatformNum)
                .build();

        TextDecorator.decorate(orgYearProfitTv, "综合年化收益率：" + data.yearProfitRate + "%")
                .setTextColor(R.color.text_red_common, "" + data.yearProfitRate + "%")
                .build();
        chartPieCp.setChartData(data.investingStatisticList);

        RetrofitHelper.getInstance().getRetrofit().create(QrCodeApi.class)
                .getQrcode(new ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<InviteCfpOutCreateQrData>> (){
                    @Override
                    public void bindViewWithDate(BaseResponseEntity<InviteCfpOutCreateQrData> response) {
                        PhotoUtil.loadImageByGlide(ctx,response.getData().getUrl(),logoIv);
                    }
                });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        backgroundAlpha(0.5f);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ctx.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ctx.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        backgroundAlpha(1f);
        super.dismiss();
    }


    @OnClick({R.id.btn_save_to_photo_album, R.id.btn_share_qq, R.id.btn_wechat_friend, R.id.btn_wechat_cricle, R.id.text_share_name})
    public void onViewClicked(View view) {
        try {

            //截图
            Bitmap bm = ScreenShotUtils.shot(ctx, cardView);
            String filename = MD5Util.MD5(System.currentTimeMillis() + "");

//            //放大图片
            if(MyApp.displayWidth < 1080) {
                float scaleRadio = 1080f / MyApp.displayWidth;
                bm = BitmapUtil.scaleImage(bm, scaleRadio, scaleRadio);
            }
            //保存
            File file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, bm, false);
            Uri uri = Uri.fromFile(file);
            imagePath = file.getAbsolutePath();



        switch (view.getId()) {
            case R.id.btn_save_to_photo_album:
                //把文件插入到系统图库
                MediaStore.Images.Media.insertImage(ctx.getContentResolver(), file.getAbsolutePath(), filename, null);
                //保存图片后发送广播通知更新数据库
                ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                ToastUtil.showCustomToast("保存成功!");
                break;
            case R.id.btn_share_qq:
                btnShareQq.setEnabled(false);
                shareService.shareImageToQQ(imagePath);
                btnShareQq.setEnabled(true);
                break;
            case R.id.btn_wechat_friend:
                btnWechatFriend.setEnabled(false);
                shareService.shareImageToWeixinFriend(imagePath);
                btnWechatFriend.setEnabled(true);
                break;
            case R.id.btn_wechat_cricle:
                btnWechatCricle.setEnabled(false);
                shareService.shareImageToWeixinCircle(imagePath);
                btnWechatCricle.setEnabled(true);
                break;
            case R.id.text_share_name:


                break;
        }
            dismiss();

        } catch (Exception e) {
            Logger.e(e);
            e.printStackTrace();
        } finally {
            dismiss();
        }
    }
}
