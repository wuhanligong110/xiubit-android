package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopCardManagerAdd;
import com.toobei.common.entity.BankCardInfo;
import com.toobei.common.utils.C;
import com.toobei.common.view.popupwindow.SelectScanWayPopup;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-添加银行卡 界面
 *
 * @date 2015-10-13
 */
public class CardManagerAdd extends TopCardManagerAdd {

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(activity, GestureActivity.class);
    }

    @Override
    protected void skipCardManageAddSuccess(BankCardInfo bankCardInfo) {
        Intent intent = new Intent(ctx, CardAddResultActivity.class);
        intent.putExtra("bankCardInfo", bankCardInfo);
        skipActivity(ctx, intent);
    }

    @Override
    protected void skipCardScan(final int type_scan) {
        new SelectScanWayPopup(ctx,new SelectScanWayPopup.Companion.ClickListener(){

            @Override
            public void onPhotoAlbumClick() {
                Intent intent = new Intent(ctx, ImageSelectActivity.class);
                intent.putExtra(com.toobei.common.utils.C.TAG, C.FOR_CARDSCAN);
                intent.putExtra("ScanType",type_scan);
                startActivity(intent);
            }

            @Override
            public void onTakePhoteClick() {
                Intent intent = new Intent(ctx, CardScanActivity.class);
                intent.putExtra("ScanType", type_scan);
                startActivity(intent);
            }
        }).showPopupWindow(rootView);

    }

    @Override
    protected int setBtnColor() {
        return R.drawable.btn_login_selector;
    }

    @Override
    protected void  goNext(BankCardInfo bankCardInfo){
        Intent intent = new Intent(ctx, WithDrawCardInfoSetActivity.class);
        intent.putExtra("bankCardInfo", bankCardInfo);
        intent.putExtra("for_what", WithDrawCardInfoSetActivity.Companion.getFOR_BINDCARD());
        startActivity(intent);
    }

}
