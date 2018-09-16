package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.RecommendProductListAdapter;
import com.v5ent.xiubit.entity.RecommendProductData;
import com.v5ent.xiubit.entity.RecommendproductDatasDataEntity;
import com.v5ent.xiubit.service.ProductService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.dialog.PromptDialogRecommend;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.ui.view.EnLetterView;
import org.xsl781.ui.xlist.XListView;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 类说明: Activity-产品或者推荐  没有绑定机构
 *
 * @author Administrator
 * @time 2016/11/9 0009 下午 5:47
 */
public class RecommendProductOrOrgNoBindActivity extends MyNetworkBaseActivity<RecommendproductDatasDataEntity> implements View.OnClickListener {
    protected XListView xListView;
    protected RecommendProductListAdapter adapter;
    protected ClearEditText cedtSearch;
    protected TextView textPrompt, textRightBtn;
    protected MySimpleTextWatcher textWatcher;
    protected String strSearch;
    protected ListBlankLayout listBlankLayout;
    protected EnLetterView rightLetter;
    protected TextView dialog;

    private ProductDetail product;
    protected List<RecommendProductData> contactsData;
    protected List<RecommendProductData> searchData;
    protected TextView textSubmit;
    private TextView rightBtn;
    private boolean hasSelectAll;
    private LinearLayout textSubmitLL; //提交按钮
    private String orgNumber;
    private int recommendType;
    private String productId;
    private boolean searchStatistics = false; //umeng 统计用


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        recommendType = getIntent().getIntExtra("recommendType", 0);
        orgNumber = getIntent().getStringExtra("orgNumber");
        if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {
            productId = getIntent().getStringExtra("productId");
            product = (ProductDetail) getIntent().getSerializableExtra("product");
        }

        super.onCreate(savedInstanceState);
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {
            headerLayout.showTitle(getResources().getString(R.string.activity_recommend_product));
        } else {
            headerLayout.showTitle(getResources().getString(R.string.activity_recommend_org));
        }
        headerLayout.showLeftBackButton();
        headerLayout.showTitleQuestion(R.drawable.img_question_white, this); //显示问号
        rightBtn = headerLayout.showRightTextButton(R.string.recommend_product_select_all, this);
        findView();

    }


    protected void findView() {

        cedtSearch = (ClearEditText) findViewById(R.id.cedt_invite_name_or_phone_search);
        textPrompt = (TextView) findViewById(R.id.text_prompt);
        textSubmit = (TextView) findViewById(R.id.btn_submit);
        textSubmit.setText(getResources().getString(R.string.submit) + "(0)");
        textSubmitLL = (LinearLayout) findViewById(R.id.btn_submitLL);
        textSubmitLL.setOnClickListener(this);
        textWatcher = new MySimpleTextWatcher();
        cedtSearch.addTextChangedListener(textWatcher);
        initXListView();
        initRightLetterView();
      //  initGuideView();
    }

//    /**
//     * 新手引导
//     */
//    private void initGuideView() {
//        if (MyApp.getInstance().getCurUserSp().isFirstGuide(GuideViewType.ACTIVITYR_ECOMMEND.toString())) {
//            this.addMineFirstGuide(GuideViewType.ACTIVITYR_ECOMMEND.toString(), GuideViewType.ACTIVITYR_ECOMMEND.getValue());
//        }
//    }

    protected void initXListView() {

        listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
        xListView = (XListView) listBlankLayout.initContentView(R.layout.list_blank_xlistview_layout);
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);
        adapter = new RecommendProductListAdapter(this);

        adapter.setCheckedListener(new RecommendProductListAdapter.OnMyCheckedListener() {

            @Override
            public void onCheckedChanged(int selectedCount, boolean isChecked) {

                textSubmit.setText(getResources().getString(R.string.submit) + "(" + selectedCount + ")");

            }
        });
        xListView.setAdapter(adapter);
    }

    protected void initRightLetterView() {
        rightLetter = (EnLetterView) ctx.findViewById(R.id.right_letter);
        dialog = (TextView) ctx.findViewById(R.id.dialog);
        rightLetter.setTextView(dialog);
        rightLetter.setOnTouchingLetterChangedListener(new LetterListViewListener());

    }

    protected class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (contactsData == null || contactsData.size() == 0) {
                return;
            }

            if (!searchStatistics) {
                searchStatistics = true;
            }
            strSearch = cedtSearch.getText().toString();

            if (strSearch.length() > 0) {
                if ((Utils.isNumeric(strSearch) && strSearch.length() > 3)) {
                    searchData = new ArrayList<RecommendProductData>();
                    for (RecommendProductData con : contactsData) {
                        if (con.getMobile().contains(strSearch)) {
                            searchData.add(con);
                        }
                    }
                    adapter.refresh(searchData);
                } else if (!Utils.isNumeric(strSearch)) {
                    searchData = new ArrayList<RecommendProductData>();
                    for (RecommendProductData con : contactsData) {
                        if (con.getUserName().contains(strSearch)) {
                            searchData.add(con);
                        }
                    }
                    adapter.refresh(searchData);
                }
            } else {
                strSearch = null;
                adapter.refresh(contactsData);
            }
        }
    }

    protected class LetterListViewListener implements EnLetterView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(String s) {
            if (adapter == null) return;

            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getDatas().get(i).getSortLetters().equals(s)) {
                    xListView.setSelection(i);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.img_title_question: //显示推荐问题

                if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {
                    PromptDialogRecommend dialogQuesiton = new PromptDialogRecommend(ctx,  getString(R.string.i_know));
                    dialogQuesiton.show();
                }else{
                    PromptDialogMsg dialogQuesiton = new PromptDialogMsg(ctx, getString(R.string.recommend_org_no_bind_question), getString(R.string.i_know));
                    dialogQuesiton.show();
                }
                break;
            case R.id.rightContainer:  //全选

                if (!hasSelectAll) {
                    adapter.setAllItemChecked(true);
                } else {
                    adapter.setAllItemChecked(false);

                }
                hasSelectAll = !hasSelectAll;
                rightBtn.setText(!hasSelectAll ? getResources().getString(R.string.recommend_product_select_all) : getResources().getString(R.string.recommend_product_select_all_cancel));
                break;

            case R.id.btn_submitLL:

                if (adapter.getCheckedCount() == 0) {
                    PromptDialog dialog = new PromptDialog(ctx, getResources().getString(R.string.recommend_no_customer), getString(R.string.confirm), getString(R.string.cancel));
                    dialog.setListener(new PromptDialog.DialogBtnOnClickListener() {

                        @Override
                        public void onClicked(PromptDialog dialog, boolean isCancel) {
                            if (!isCancel) {
                                recommendByChoose();
                            }
                        }
                    });
                    dialog.show();
                    return;
                }

                // 当推荐人数不是0时 推荐机构或者产品的吐司
                PromptDialog dialog = null;
                if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {
                    dialog = new PromptDialog(ctx, getResources().getString(R.string.recommend_pro_has_customer), getString(R.string.confirm), getString(R.string.cancel));
                    dialog.setListener(new PromptDialog.DialogBtnOnClickListener() {

                        @Override
                        public void onClicked(PromptDialog dialog, boolean isCancel) {
                            if (!isCancel) {
                                recommendByChoose();
                            }
                        }
                    });
                } else {
                    dialog = new PromptDialog(ctx, getResources().getString(R.string.recommend_org_has_customer), getString(R.string.confirm), getString(R.string.cancel));
                    dialog.setListener(new PromptDialog.DialogBtnOnClickListener() {

                        @Override
                        public void onClicked(PromptDialog dialog, boolean isCancel) {
                            if (!isCancel) {
                                recommendByChoose();
                            }
                        }
                    });
                }
                dialog.show();

                break;
        }

    }

    protected void recommendByChoose() {
        if (adapter == null) {
            return;
        }
        final List<RecommendProductData> checkedDatas = adapter.getCheckedDatas(); // 获取选择的客户集合
        ArrayList<String> userIdss = new ArrayList<String>();

        for (RecommendProductData cus : checkedDatas) {
            String mobile = cus.getMobile();
            userIdss.add(mobile);
        }

        if (checkedDatas == null) {
            return;
        }

        new MyNetAsyncTask(ctx, true) {

            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {

                if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {  //推荐产品
                    response = MyApp.getInstance().getHttpService().recommendByChoose(MyApp.getInstance().getLoginService().token, productId, getStringUserId(checkedDatas)); // 传入所有选中的userID
                } else {
                    response = MyApp.getInstance().getHttpService().platfromRecommendByChoose(MyApp.getInstance().getLoginService().token, orgNumber, getStringUserId(checkedDatas)); // 传入所有选中的userID

                }
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    String code = response.getCode();
                    if (code.equals("0")) {
                        if (adapter.getCheckedCount() != 0) {
                            ToastUtil.showCustomToast(getResources().getString(R.string.recommend_product_success));
                        }
                        if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {  //推荐产品
                            product.setCfpRecommend(checkedDatas.size() + "");
                            ProductService.notifyUiAndData(product);
                        }
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();

        Intent intent = new Intent();
        intent.putExtra("RecommendUserSize", checkedDatas.size());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_recommend_product_nobind_org;
    }

    @Override
    protected RecommendproductDatasDataEntity onLoadDataInBack() throws Exception {
        if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {  //推荐产品
            return MyApp.getInstance().getHttpService().recommendChooseList(MyApp.getInstance().getLoginService().token, productId, "");
        } else { //推荐机构
            return MyApp.getInstance().getHttpService().platfromRecommendChooseList(MyApp.getInstance().getLoginService().token, orgNumber, "");
        }
    }

    @Override
    protected void onRefreshDataView(RecommendproductDatasDataEntity data) {

        contactsData = data.getData().getAllFeeList();//未对接机构的所有的投资人

        if (contactsData != null && contactsData.size() > 0) {
            List<String> mobiles = null;
            if (contactsData == null || contactsData.size() == 0) {
                listBlankLayout.showBlank(R.string.recommend_product_haveno_cus);
            } else {
                listBlankLayout.showContentView();
                Collections.sort(contactsData, new PinyinComparator());
                adapter.refresh(contactsData);
            }
        }

    }


    class PinyinComparator implements Comparator<RecommendProductData> {
        public int compare(RecommendProductData o1, RecommendProductData o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        }
    }

    protected String getStringUserId(List<RecommendProductData> datas) {
        if (datas == null || datas.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {


            if (datas.get(i).getUserId() == null || datas.get(i).getUserId().isEmpty()) {
                continue;
            }
            sb.append(datas.get(i).getUserId());
            if (i < datas.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString().replaceAll(" ", "");
    }

    @Override
    public void finish() {
        super.finish();
    }
}
