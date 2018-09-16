package com.toobei.tb.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.common.entity.ProductClassifyStatisticsEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.ProductsPagerAdapter;
import com.toobei.tb.view.MyPagerSlidingTabStrip;

import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.SystemTool;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:  t呗 产品列表 多项产品列表滑动
 *
 * @author qingyechen
 * @time 2016/12/27 0027 下午 3:48
 */
public class ProductViewPagerActivity extends MyBaseActivity implements View.OnClickListener {

    public ViewPager mPager;
    public ProductsPagerAdapter adapter;
    private String cateId;/*非必需 默认根据不同的app类型查询对应的所有分类信息 1-热门产品 2-新手产品 3-短期产品 4-高收益产品
                     5-稳健收益产品 801-理财师推荐产品 802-热推产品 901-首投标 902-复投标 多个一起查询的时候请使用,分开
                    如：1,2,3,4,5,801,901,902 不传时则查询所有的产品分类*/
    private ProductClassifyStatisticsEntity response;
    private List<ProductClassifyStatisticsDetail> productClassdatas;
    private ViewStub mContentStub;
    private ViewStub mFailStub;
    private View mContentView;
    private View mLoadFailView;
    private MyPagerSlidingTabStrip titleTL;
    private String listType;
    private ProductClassifyStatisticsEntity productClassifyStatisticsEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        initView();
        listType = getIntent().getStringExtra("listType");//ListType

        if (listType.equals(ProductType.PRODUCTTYPE_CLASSIFY_TYPE)) { //全部标的
            cateId = TextUtils.isEmpty(getIntent().getStringExtra("cateId")) ? "902" : getIntent().getStringExtra("cateId");//默认复投标
        } else { //短期长期高收益
            cateId = TextUtils.isEmpty(getIntent().getStringExtra("cateId")) ? "4" : getIntent().getStringExtra("cateId");//高收益
        }
        productClassifyStatisticsEntity = (ProductClassifyStatisticsEntity) getIntent().getSerializableExtra("response");//ListType
        getData(cateId, false);//默认复投标

    }

    private void initView() {
        setTranslucentStatus(true);
        // 顶部标题栏
        RelativeLayout mHeadRe = (RelativeLayout) findViewById(R.id.headRe);
        findViewById(R.id.backBtn).setOnClickListener(this);
        setHeadViewCoverStateBar(mHeadRe);
        titleTL = (MyPagerSlidingTabStrip) findViewById(R.id.titleTL);
        titleTL.setTabUnSelectedColor(Color.parseColor("#bbffffff"));
        mContentStub = (ViewStub) findViewById(R.id.stub_content);
        mFailStub = (ViewStub) findViewById(R.id.stub_fail);
        mContentStub.setLayoutResource(R.layout.fragment_product_and_institution);
        mContentView = mContentStub.inflate();
        mPager = (ViewPager) mContentView.findViewById(R.id.product_viewpager);
    }

    //添加系统状态栏高度
    public void setHeadViewCoverStateBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = PixelUtil.dip2px(this, 44) + SystemTool.getStatusBarHeight(this);
        }
    }

    /**
     * 功能: 接口名称 全部标的列表 v-2.0.1
     * 请求类型 get
     * 请求Url /product/productTypeList/2.0.1
     * <p>
     * cateIdList 非必需 2-新手产品 901-首投标 902-复投标 多个一起查询的时候请使用,分开 如：2,901,902 不传时则查询所有的（2,901,902）产品分类
     */
    public void getData(String cateId, boolean openDialog) {
        this.cateId = cateId;

        new MyNetAsyncTask(ctx, openDialog) {

            @Override
            protected void doInBack() throws Exception {
                if (ProductType.PRODUCTTYPE_CLASSIFY_TYPE.equals(listType)) { //全部标的

                    response = MyApp.getInstance().getHttpService().productTypeList("", "");
                } else {
                    if (productClassifyStatisticsEntity != null) {
                        response = productClassifyStatisticsEntity;
                    } else {
                        response = MyApp.getInstance().getHttpService().productClassifyStatistics201(MyApp.getInstance().getLoginService().token, "");
                    }
                }

            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData() != null && response.getData().getDatas() != null && response.getData().getDatas().size() > 0) {
                            productClassdatas = response.getData().getDatas();
                            if (mLoadFailView != null) {
                                mLoadFailView.setVisibility(View.GONE);
                            }
                            mContentView.setVisibility(View.VISIBLE);
                            initViewPager();
                        }
                    } else {
                        showFail();
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());

                    }
                } else {
                    showFail();
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));

                }
            }


        }.execute();


    }

    /**
     * 初始化或者从home页跳转切换 Viewpager 中的fragment
     */
    private void initViewPager() {
        if (adapter == null) {
            adapter = new ProductsPagerAdapter(this, getSupportFragmentManager(), productClassdatas);
            mPager.setAdapter(adapter);
            //mPager.setOffscreenPageLimit(productClassdatas.size());
            titleTL.setViewPager(mPager);
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < productClassdatas.size(); i++) {
            if (cateId.equals(productClassdatas.get(i).getCateId())) {
                mPager.setCurrentItem(i);
            }
        }
    }

    protected void showFail() {

        mContentView.setVisibility(View.GONE);
        if (mLoadFailView == null) {
            View view = mFailStub.inflate();
            ImageView iv = (ImageView) view.findViewById(com.toobei.common.R.id.network_click);
            iv.setOnClickListener(this);
            mLoadFailView = view;
        }
        mLoadFailView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.network_click:

                getData(cateId, true);//默认复投标
                break;
            case R.id.backBtn:
                finish();
                break;
        }
    }

    /**
     * 页面显示的产品列表类型
     */
    public interface ProductType {
        /**
         * 全部标的
         */
        String PRODUCTTYPE_CLASSIFY_TYPE = "PRODUCTTYPE_CLASSIFY_TYPE";
        /**
         * 短期长期高收益
         */
        String PRODUCTTYPE_DATE_LIMIT_TYPE = "PRODUCTTYPE_DATE_LIMIT_TYPE";
    }

}
