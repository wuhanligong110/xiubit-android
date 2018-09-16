package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.common.view.dialog.PromptDialog;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.RecommendProductListAdapter;
import com.v5ent.xiubit.entity.RecommendProductData;
import com.v5ent.xiubit.entity.RecommendproductDatasDataEntity;
import com.v5ent.xiubit.service.ProductService;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.MyPagerSlidingTabStrip;
import com.v5ent.xiubit.view.dialog.PromptDialogRecommend;

import org.xsl781.data.BaseCheckListAdapter;
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
 * Activity-产品或者机构推荐(绑定了机构的)
 *
 * @author Administrator
 * @time 2016/10/26 0026 上午 10:52
 */
public class RecommendProductOrOrgActivity extends MyNetworkBaseActivity<RecommendproductDatasDataEntity> implements View.OnClickListener, ViewPager.OnPageChangeListener {

    protected ClearEditText cedtSearch;
    protected MySimpleTextWatcher textWatcher;
    private MyPagerSlidingTabStrip tabs;
    private ViewPager vPager;
    private TextView rightBtn;
    protected String strSearch;
    private List<View> pagerViews = new ArrayList<View>();
    private int currentPage = 0;
    private ProductDetail product;
    private String orgNumber;
    private int recommendType;// 推荐类型

    // 有佣金的--------------------------------------//
    protected XListView xListViewHasFee;
    protected RecommendProductListAdapter adapterHasFee;
    protected TextView textPromptHasFee;
    protected ListBlankLayout listBlankLayoutHasFee;
    protected EnLetterView rightLetterHasFee;
    protected TextView dialogHasFee;
    protected List<RecommendProductData> contactsDataHasFee;
    protected List<RecommendProductData> searchDataHasFee;
    protected TextView textInvestHasFee;
    private boolean hasSelectAllHasFee;

    // 没有佣金的-------------------------------------//

    protected XListView xListViewNoFee;
    protected RecommendProductListAdapter adapterNoFee;
    protected TextView textPromptNoFee;
    protected ListBlankLayout listBlankLayoutNoFee;
    protected EnLetterView rightLetterNoFee;
    protected TextView dialogNoFee;
    protected List<RecommendProductData> contactsDataNoFee;
    protected List<RecommendProductData> searchDataNoFee;
    protected TextView textSubmitNoFee;
    private boolean hasSelectAllNoFee;
    private String productId;
    boolean searchStatistics = false; //uemng统计搜索用的
    private boolean switchHasCommissionStatistics = false;  //uemng统计搜索用的 切换有无佣金

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
        headerLayout.showTitleQuestion(R.drawable.img_question_white, this);
        headerLayout.showLeftBackButton();
        rightBtn = headerLayout.showRightTextButton(R.string.recommend_product_select_all, this);
        findView();
      //  initGuideView();
    }


    protected void findView() {

        cedtSearch = (ClearEditText) findViewById(R.id.cedt_invite_name_or_phone_search);
        textPromptHasFee = (TextView) findViewById(R.id.text_prompt);
        tabs = (MyPagerSlidingTabStrip) findViewById(R.id.recommend_product_tabs);
        vPager = (ViewPager) findViewById(R.id.recommend_product_vPager);
        textWatcher = new MySimpleTextWatcher();
        cedtSearch.addTextChangedListener(textWatcher);
    }

//    /**
//     * 新手引导
//     */
//    private void initGuideView() {
//        if (MyApp.getInstance().getCurUserSp().isFirstGuide(GuideViewType.ACTIVITYR_ECOMMEND.toString())) {
//            this.addMineFirstGuide(GuideViewType.ACTIVITYR_ECOMMEND.toString(), GuideViewType.ACTIVITYR_ECOMMEND.getValue());
//        }
//    }
    protected View getNoFeeView() {

        View noFeerootView = LayoutInflater.from(ctx).inflate(R.layout.layout_recommend_product_bind_org_layout, null);
        listBlankLayoutNoFee = (ListBlankLayout) noFeerootView.findViewById(R.id.list_blank_layout);
        xListViewNoFee = (XListView) listBlankLayoutNoFee.initContentView(R.layout.list_blank_xlistview_layout);
        textSubmitNoFee = (TextView) noFeerootView.findViewById(R.id.btn_submit);
        textSubmitNoFee.setText(getResources().getString(R.string.submit) + "(0)");
        xListViewNoFee.setPullRefreshEnable(false);
        xListViewNoFee.setPullLoadEnable(false);
        adapterNoFee = new RecommendProductListAdapter(this);
        adapterNoFee.setCheckedListener(new RecommendProductListAdapter.OnMyCheckedListener() {

            @Override
            public void onCheckedChanged(int selectedCount, boolean isChecked) {

                textSubmitNoFee.setText(getResources().getString(R.string.submit) + "("  + selectedCount + ")");

            }
        });
        xListViewNoFee.setAdapter(adapterNoFee);
        rightLetterNoFee = (EnLetterView) noFeerootView.findViewById(R.id.right_letter);//拖动
        dialogNoFee = (TextView) noFeerootView.findViewById(R.id.dialog);
        textPromptNoFee = (TextView) noFeerootView.findViewById(R.id.text_prompt);
        textPromptNoFee.setText(getResources().getString(R.string.recommend_product_prompt));
        rightLetterNoFee.setTextView(dialogNoFee);
        rightLetterNoFee.setOnTouchingLetterChangedListener(new LetterListViewListener());

        noFeerootView.findViewById(R.id.btn_submitLL).setOnClickListener(this);//提交
        return noFeerootView;
    }

    protected View getHasFeeView() {

        View hasFeerootView = LayoutInflater.from(ctx).inflate(R.layout.layout_recommend_product_bind_org_layout, null);
        listBlankLayoutHasFee = (ListBlankLayout) hasFeerootView.findViewById(R.id.list_blank_layout);
        textInvestHasFee = (TextView) hasFeerootView.findViewById(R.id.btn_submit);
        textInvestHasFee.setText(getResources().getString(R.string.submit) + "(0)");
        hasFeerootView.findViewById(R.id.btn_submitLL).setOnClickListener(this);

        xListViewHasFee = (XListView) listBlankLayoutHasFee.initContentView(R.layout.list_blank_xlistview_layout);
        xListViewHasFee.setPullRefreshEnable(false);
        xListViewHasFee.setPullLoadEnable(false);
        adapterHasFee = new RecommendProductListAdapter(this);
        adapterHasFee.setCheckedListener(new RecommendProductListAdapter.OnMyCheckedListener() {
            @Override
            public void onCheckedChanged(int selectedCount, boolean isChecked) {
                textInvestHasFee.setText(getResources().getString(R.string.submit) + "(" + selectedCount + ")" );
            }
        });
        xListViewHasFee.setAdapter(adapterHasFee);
        rightLetterHasFee = (EnLetterView) hasFeerootView.findViewById(R.id.right_letter);//拖动
        dialogHasFee = (TextView) hasFeerootView.findViewById(R.id.dialog);
        rightLetterHasFee.setTextView(dialogHasFee);
        rightLetterHasFee.setOnTouchingLetterChangedListener(new LetterListViewListener());
        return hasFeerootView;
    }


    //搜索栏监听
    protected class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!searchStatistics) {
                searchStatistics = true;
            }
            strSearch = cedtSearch.getText().toString();
            if (currentPage == 0) {//有佣金时
                if (contactsDataHasFee == null || contactsDataHasFee.size() == 0) {
                    return;
                }
                if (strSearch.length() > 0) {
                    if ((Utils.isNumeric(strSearch) && strSearch.length() > 3)) {
                        searchDataHasFee = new ArrayList<RecommendProductData>();
                        for (RecommendProductData con : contactsDataHasFee) {
                            if (con.getMobile().contains(strSearch)) {
                                searchDataHasFee.add(con);
                            }
                        }
                        adapterHasFee.refresh(searchDataHasFee);
                    } else if (!Utils.isNumeric(strSearch)) {
                        searchDataHasFee = new ArrayList<RecommendProductData>();
                        for (RecommendProductData con : contactsDataHasFee) {
                            if (con.getUserName().contains(strSearch)) {
                                searchDataHasFee.add(con);
                            }
                        }
                        adapterHasFee.refresh(searchDataHasFee);
                    }
                } else {
                    strSearch = null;
                    adapterHasFee.refresh(contactsDataHasFee);
                }


            } else { //没有佣金的时候
                if (contactsDataNoFee == null || contactsDataNoFee.size() == 0) {
                    return;
                }
                if (strSearch.length() > 0) {
                    if ((Utils.isNumeric(strSearch) && strSearch.length() > 3)) {
                        searchDataNoFee = new ArrayList<RecommendProductData>();
                        for (RecommendProductData con : contactsDataNoFee) {
                            if (con.getMobile().contains(strSearch)) {
                                searchDataNoFee.add(con);
                            }
                        }
                        adapterNoFee.refresh(searchDataNoFee);
                    } else if (!Utils.isNumeric(strSearch)) {
                        searchDataNoFee = new ArrayList<RecommendProductData>();
                        for (RecommendProductData con : contactsDataNoFee) {
                            if (con.getUserName().contains(strSearch)) {
                                searchDataNoFee.add(con);
                            }
                        }
                        adapterNoFee.refresh(searchDataNoFee);
                    }
                } else {
                    strSearch = null;
                    adapterNoFee.refresh(contactsDataNoFee);
                }

            }


        }
    }

    //右侧滑动监听
    protected class LetterListViewListener implements EnLetterView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(String s) {

            //有佣金
            if (currentPage == 0) {
                if (adapterHasFee == null) return;
                for (int i = 0; i < adapterHasFee.getCount(); i++) {
                    if (adapterHasFee.getDatas().get(i).getSortLetters().equals(s)) {
                        xListViewHasFee.setSelection(i);
                    }
                }
                //没有佣金
            } else {
                if (adapterNoFee == null) return;
                for (int i = 0; i < adapterNoFee.getCount(); i++) {
                    if (adapterNoFee.getDatas().get(i).getSortLetters().equals(s)) {
                        xListViewNoFee.setSelection(i);
                    }
                }

            }
        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.img_title_question: //显示推荐问题
                PromptDialogRecommend dialogQuesiton = new PromptDialogRecommend(ctx, getString(R.string.i_know));
                dialogQuesiton.show();
                break;
            case R.id.rightContainer:  //全选


                //有佣金
                if (currentPage == 0) {
                    if (!hasSelectAllHasFee) {
                        adapterHasFee.setAllItemChecked(true);
                        rightBtn.setText(getResources().getString(R.string.recommend_product_select_all));

                    } else {
                        adapterHasFee.setAllItemChecked(false);
                        rightBtn.setText(getResources().getString(R.string.recommend_product_select_all_cancel));
                    }

                    hasSelectAllHasFee = !hasSelectAllHasFee;
                    rightBtn.setText(!hasSelectAllHasFee ? getResources().getString(R.string.recommend_product_select_all) : getResources().getString(R.string.recommend_product_select_all_cancel));
                }

                //没有佣金
                else {
                    if (!hasSelectAllNoFee) {
                        adapterNoFee.setAllItemChecked(true);
                        rightBtn.setText(getResources().getString(R.string.recommend_product_select_all));

                    } else {
                        adapterNoFee.setAllItemChecked(false);
                        rightBtn.setText(getResources().getString(R.string.recommend_product_select_all_cancel));
                    }

                    hasSelectAllNoFee = !hasSelectAllNoFee;
                    rightBtn.setText(!hasSelectAllNoFee ? getResources().getString(R.string.recommend_product_select_all) : getResources().getString(R.string.recommend_product_select_all_cancel));
                }
                break;

            case R.id.btn_submitLL:
                if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {  //推荐产品
                } else {  //推荐平台
                }


                BaseCheckListAdapter adapter;
                adapter = currentPage == 0 ? adapterHasFee : adapterNoFee; //当前选择的是有佣金还是没佣金

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
                if (recommendType == C.RECOMMEND_TYPE_PRODUCT) {  //推荐产品

                    dialog = new PromptDialog(ctx, getResources().getString(R.string.recommend_pro_has_customer), getString(R.string.confirm), getString(R.string.cancel));
                    dialog.setListener(new PromptDialog.DialogBtnOnClickListener() {

                        @Override
                        public void onClicked(PromptDialog dialog, boolean isCancel) {
                            if (!isCancel) {
                                recommendByChoose();
                            }
                        }
                    });

                } else { //推荐平台
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


    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_recommend_product_bind_org;
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

        // 2016/10/25 0025
        contactsDataHasFee = data.getData().getHaveFeeList();//
        contactsDataNoFee = data.getData().getNotHaveFeeList();

        /*--------------------------初始化ViewPager---------------------------*/
        pagerViews.add(getHasFeeView());
        pagerViews.add(getNoFeeView());
        vPager.setAdapter(new MyPagerAdapter(pagerViews));
        vPager.addOnPageChangeListener(this);
        tabs.setViewPager(vPager);
        /*--------------------------初始化ViewPager---------------------------*/
        if (contactsDataHasFee != null && contactsDataHasFee.size() > 0) {
            List<String> mobiles = null;
            if (contactsDataHasFee == null || contactsDataHasFee.size() == 0) {
                listBlankLayoutHasFee.showBlank(R.string.recommend_product_haveno_cus);
            } else {
                listBlankLayoutHasFee.showContentView();
                Collections.sort(contactsDataHasFee, new PinyinComparator());
                adapterHasFee.refresh(contactsDataHasFee);
            }
        }
        if (contactsDataNoFee != null && contactsDataNoFee.size() > 0) {
            List<String> mobiles = null;
            if (contactsDataNoFee == null || contactsDataNoFee.size() == 0) {
                listBlankLayoutNoFee.showBlank(R.string.recommend_product_haveno_cus);
            } else {
                listBlankLayoutNoFee.showContentView();
                Collections.sort(contactsDataNoFee, new PinyinComparator());
                adapterNoFee.refresh(contactsDataNoFee);
            }
        }

    }


    protected void recommendByChoose() {

        final BaseCheckListAdapter adapter;

        adapter = currentPage == 0 ? adapterHasFee : adapterNoFee;

        if (adapter == null /*|| investRecordAdapter.getCount() == 0*/) {
            return;
        }
        final List<RecommendProductData> checkedDatas = adapter.getCheckedDatas(); // 获取选择的客户集合
        ArrayList<String> userIdss = new ArrayList<String>();

        if (checkedDatas == null /*|| checkedDatas.size() == 0*/) {
            finish();
            return;
        }
        for (RecommendProductData cus : checkedDatas) {
            String mobile = cus.getMobile();
            userIdss.add(mobile);
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
    }


    class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public int getCount() {
            return mListViews.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? getResources().getString(R.string.recommend_product_havefee) + "(" +
                    contactsDataHasFee.size() + ")" : getResources().getString(R.string.recommend_product_nofee) + "(" + contactsDataNoFee.size() + ")";
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        currentPage = position;
        boolean hasSelectAll = position == 0 ? hasSelectAllHasFee : hasSelectAllNoFee;
        rightBtn.setText(!hasSelectAll ? getResources().getString(R.string.recommend_product_select_all) : getResources().getString(R.string.recommend_product_select_all_cancel));

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (!switchHasCommissionStatistics) {
            switchHasCommissionStatistics = true;
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
}
