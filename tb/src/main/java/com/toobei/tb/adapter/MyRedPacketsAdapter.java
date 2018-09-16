package com.toobei.tb.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.view.MarqueeTextView;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.entity.RedPacket;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.view.RoundImageView;

import java.util.List;


public class MyRedPacketsAdapter extends BaseListAdapter<RedPacket> {

    private Context context;
    private LayoutInflater inflater;

    private int flag;//标示1：可用红包    2：已使用     3：已过期

    public MyRedPacketsAdapter(Context context, List<RedPacket> datas, int flag) {
        super(context, datas);
        this.flag = flag;

        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    static class HolderView {
        TextView redpaperMoneyTv; //红包金额
        RelativeLayout itemRe;
        LinearLayout busTypeTv;
        TextView titileDesTv; // 投资期限

        TextView dateStrTv;
        ImageView mycfpSend; //理财师派发
        ImageView useStatusIv;
        RoundImageView userImageIV;
        ImageView redpacketsendIV;
        TextView redpackageNameTV;//mingz
        TextView investLlimitTV; //投资限制
        TextView platformTV;//限制平台
        MarqueeTextView productNameTV;//限制产品名称
        TextView redpaperMoney00Tv; //RMB符号
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(int arg0, View convertview, ViewGroup arg2) {
        HolderView mHolderView = null;

        if (convertview == null) {
            convertview = inflater.inflate(R.layout.item_my_packets, null);
            mHolderView = new HolderView();
            mHolderView.redpaperMoney00Tv = (TextView) convertview.findViewById(R.id.redpaperMoneyTv00);//红包金额
            mHolderView.redpaperMoneyTv = (TextView) convertview.findViewById(R.id.redpaperMoneyTv);//红包金额
            mHolderView.redpackageNameTV = (TextView) convertview.findViewById(R.id.text_mission_name); //红包描述
            mHolderView.investLlimitTV = (TextView) convertview.findViewById(R.id.text_investLlimit); //投资限制text_investLlimit
            mHolderView.platformTV = (TextView) convertview.findViewById(R.id.text_redpackage_platform); //投资限制text_investLlimit
            mHolderView.productNameTV = (MarqueeTextView) convertview.findViewById(R.id.text_redpackage_productName); //产品名称 （产品限制等于1时有效）
            mHolderView.productNameTV.setSelected(true);
            mHolderView.itemRe = (RelativeLayout) convertview.findViewById(R.id.itemRe);
            mHolderView.busTypeTv = (LinearLayout) convertview.findViewById(R.id.redpackage_busy_typeLL);
            mHolderView.titileDesTv = (TextView) convertview.findViewById(R.id.text_redpackage_des); //红包描述
            mHolderView.dateStrTv = (TextView) convertview.findViewById(R.id.availableDateTv);
            mHolderView.userImageIV = (RoundImageView) convertview.findViewById(R.id.avatar);
            mHolderView.useStatusIv = (ImageView) convertview.findViewById(R.id.flagIv);
            mHolderView.mycfpSend = (ImageView) convertview.findViewById(R.id.mycfp_send_iv);
            convertview.setTag(mHolderView);
        } else {
            mHolderView = (HolderView) convertview.getTag();
        }
        // 红包参数
        RedPacket redPacket = datas.get(arg0);
        String rid = redPacket.getRid(); //            "rid" : "123456" //红包编号
        String redpacketType = redPacket.getRedpacketType();

        String useStatus = redPacket.getUseStatus(); //            "useStatus":"0",//已派发红包使用状态：0=未使用|1=已使用|2=已过期
        String userName = redPacket.getUserName(); //            "userName":"",//用户名称
        String userMobile = redPacket.getUserMobile(); //            "userMobile":""//用户手机
        String userImage = redPacket.getUserImage(); //            "userMobile":""//用户手机

        if ("0".equals(redpacketType)) {
            mHolderView.mycfpSend.setVisibility(View.INVISIBLE);
        } else {
            mHolderView.mycfpSend.setVisibility(View.VISIBLE);
        }

        mHolderView.redpackageNameTV.setText(redPacket.getName());//红包name
        switch (redPacket.getInvestLimit()) {  //投资限制 0=不限|1=用户首投|2=平台首投
            case 0:
                mHolderView.investLlimitTV.setText("不限");
                break;
            case 1:
                mHolderView.investLlimitTV.setText("用户首投");
                break;
            case 2:
                mHolderView.investLlimitTV.setText("平台首投");
                break;
        }
        mHolderView.platformTV.setText(redPacket.getPlatformLimit() ? redPacket.getPlatform() : "不限"); //限制平台 平台限制 =true 有效

        String productLimitName = "";
        String deadline = redPacket.getDeadline();
        switch (redPacket.getProductLimit()) {
            case 0:
                productLimitName = "不限";
                break;
            case 1:
                productLimitName = redPacket.getProductName();
                break;
            case 2:
                productLimitName = deadline + "天产品";
                break;
            case 3:
                productLimitName = deadline + "天以上产品(含" + deadline + "天)";
                break;
        }
        mHolderView.productNameTV.setText(productLimitName); //产品名称 （产品限制等于1时有效）
        mHolderView.redpaperMoneyTv.setText("" + redPacket.getRedpacketMoney()); //红包金额
        mHolderView.titileDesTv.setText("" + redPacket.getRemark()); //红包描述
        mHolderView.dateStrTv.setText(redPacket.getExpireTime());
        int grayCommom = ContextCompat.getColor(context,R.color.text_gray_common);
        int yellowCommom = ContextCompat.getColor(context,R.color.text_yellow_redpackage);
        int redCommom = ContextCompat.getColor(context,R.color.text_red_redpackage);
        int whiteCommom = ContextCompat.getColor(context,R.color.WHITE);
        if (flag == 1) { //可用红包

            mHolderView.redpaperMoneyTv.setTextColor(redCommom);
            mHolderView.redpaperMoney00Tv.setTextColor(redCommom);
            mHolderView.itemRe.setBackgroundResource(R.drawable.iv_redpacket_itembg_red_small);
            mHolderView.busTypeTv.setBackgroundResource(R.drawable.iv_redpacket_item_red);
            mHolderView.useStatusIv.setVisibility(View.GONE);
            mHolderView.redpackageNameTV.setTextColor(yellowCommom);
            mHolderView.mycfpSend.setImageResource(R.drawable.mycfp_send_img);

        } else if (flag == 2) {//已用红包
            mHolderView.mycfpSend.setImageResource(R.drawable.mycfp_send_gray_img);
            mHolderView.useStatusIv.setVisibility(View.VISIBLE);
            MyApp.getInstance().getUserService().displayUserFace(MyApp.getInstance().getHttpService().getImageServerBaseUrl() + userImage, mHolderView.userImageIV, true);
            mHolderView.redpaperMoneyTv.setTextColor(grayCommom);
            mHolderView.redpaperMoney00Tv.setTextColor(grayCommom);
            mHolderView.itemRe.setBackgroundResource(R.drawable.iv_redpacket_itembg_gray_small);
            mHolderView.busTypeTv.setBackgroundResource(R.drawable.iv_redpacket_item_grey);
            mHolderView.redpackageNameTV.setTextColor(grayCommom);
            mHolderView.useStatusIv.setBackgroundResource(R.drawable.iv_redpacket_already_used);
        } else if (flag == 3) { // 过期红包
            mHolderView.mycfpSend.setImageResource(R.drawable.mycfp_send_gray_img);
            mHolderView.useStatusIv.setVisibility(View.VISIBLE);
            mHolderView.itemRe.setBackgroundResource(R.drawable.iv_redpacket_itembg_gray_small);
            mHolderView.busTypeTv.setBackgroundResource(R.drawable.iv_redpacket_item_grey);
            mHolderView.useStatusIv.setBackgroundResource(R.drawable.iv_redpadket_out_of_date);
            mHolderView.redpackageNameTV.setTextColor(grayCommom);
            mHolderView.redpaperMoneyTv.setTextColor(grayCommom);
            mHolderView.redpaperMoney00Tv.setTextColor(grayCommom);
        }

        return convertview;
    }
}
