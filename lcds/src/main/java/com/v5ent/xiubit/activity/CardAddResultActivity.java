package com.v5ent.xiubit.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.entity.BankCardInfo;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.xsl781.utils.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/28
 */

public class CardAddResultActivity extends MyTitleBaseActivity {

    @BindView(R.id.bankCardInfoTv)
    TextView mBankCardInfoTv;
    @BindView(R.id.goRedPocketLl)
    LinearLayout mGoRedPocketLl;
    @BindView(R.id.bottomRemindTv)
    TextView mBottomRemindTv;
    @BindView(R.id.completeBtn)
    View completeBtn;
    private BankCardInfo mBankCardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBankCardInfo = (BankCardInfo) getIntent().getSerializableExtra("bankCardInfo");
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        headerLayout.showLeftBackButton();
        headerLayout.showTitle("绑定银行卡");
        if (mBankCardInfo != null || !TextUtil.isEmpty(mBankCardInfo.getBankCard())) {
            mBankCardInfoTv.setText(mBankCardInfo.getRemark());
        }
// 4.5.4 取消首次绑卡 20元红包提示
//        if ("1".equals(mBankCardInfo.getHaveBind())) {
//            mGoRedPocketLl.setVisibility(View.GONE);
//        } else {
//            mGoRedPocketLl.setVisibility(View.VISIBLE);
//        }

        TextDecorator.decorate(mBottomRemindTv, getResources().getString(R.string.card_bind_bottom_remind))
                .makeTextClickable(new TextDecorator.OnTextClickListener() {
                    @Override
                    public void onClick(View view, String text) {
                        callPhone();
                    }
                }, false, "400-888-6987")
                .setTextColor(R.color.text_blue_common, "400-888-6987")
                .build();

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callPhone() {
        AndPermission.with(ctx)
                .requestCode(MY_PERMISSIONS_REQUEST_DIAL)
                .permission(Manifest.permission.CALL_PHONE)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        
                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, "确认拨打客服电话?",  "400-888-6987");
                        dialog.setBtnPositiveColor(R.color.text_blue_common);
                        dialog.show();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Logger.e("用户拒绝打电话权限");
                    }
                })
                .start();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_card_add_result;
    }

    @OnClick(R.id.goRedPocketLl)
    public void onViewClicked() {
        showActivity(ctx, CouponActivity.class);
    }
}
