package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.toobei.common.view.ListBlankLayout;
import com.toobei.common.view.listView.PinnedHeaderListView;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.RedeemSoonListAdapter;
import com.v5ent.xiubit.entity.CustomerTrade;
import com.v5ent.xiubit.entity.CustomerTradeListDatasDataEntity;
import com.v5ent.xiubit.util.C;

import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明： Activity-客户回款
 *
 * @date 2016-5-19
 */
public class RedeemList extends MyNetworkBaseActivity<CustomerTradeListDatasDataEntity> implements IXListViewListener, View.OnClickListener {

    private PinnedHeaderListView listView;
    private RedeemSoonListAdapter adapter;
    private ListBlankLayout listBlankLayout;
    private int pageIndex = 1;
    private ImageButton backBtn; //返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initTopTitle();
        //initGuideView();
    }



    private void initTopTitle() {
        headerLayout.showTitle(getResources().getString(R.string.activity_redeemlist));
        headerLayout.showLeftBackButton();
    }
//    /**
//     * 新手引导
//     */
//    private void initGuideView() {
//        if (MyApp.getInstance().getCurUserSp().isFirstGuide(GuideViewType.ACTIVITY_REDEEMLIST.toString())) {
//            this.addMineFirstGuide(GuideViewType.ACTIVITY_REDEEMLIST.toString(), GuideViewType.ACTIVITY_REDEEMLIST.getValue());
//        }
//    }

    private void initView() {
        initXListView();
    }

    private void initXListView() {
        listBlankLayout = (ListBlankLayout) findViewById(R.id.list_blank_layout);
        listView = (PinnedHeaderListView) listBlankLayout.initContentView(R.layout.list_blank_pined_head_listview_layout);
        listView.setXListViewListener(this);
        backBtn = (ImageButton) findViewById(R.id.redeemlist_iv_back_to_top); //点击返回顶部
        backBtn.setOnClickListener(this);

        //  需求改为不固定显示头部..
        View headview = getLayoutInflater().inflate(R.layout.item_redeem_soon_list_head_pind, listView, false);
       listView.setPinnedHeader(headview);


        adapter = new RedeemSoonListAdapter(this, backBtn);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);

    }

    public void onLoad() {
        if (listView != null) {
            listView.stopRefresh();
            listView.stopLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        loadData(false);
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        loadData(false);
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_redeem_soon;
    }

    @Override
    protected CustomerTradeListDatasDataEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().customerExpireRedeemPageList(MyApp.getInstance().getLoginService().token, "", pageIndex);
    }

    @Override
    protected void onRefreshDataView(CustomerTradeListDatasDataEntity response) {
        listView.setPullLoadEnable(true);
        List<CustomerTrade> datas = response.getData().getDatas();

        pageIndex = response.getData().getPageIndex();

        if (datas == null || datas.size() == 0) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//

//            listBlankLayout.addView((View.inflate(this, R.layout.layout_blank_redeem_empty, null)), params);
            listBlankLayout.showBlankImageAndTextAndButton(R.drawable.img_no_data_blank, getString(R.string.redeem_list_blank_prompt), getString(R.string.invest_list_blank_btn_text), new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    showActivity(ctx, InviteCfpQr.class);
                    Intent intent = new Intent(ctx, CommonFragmentActivity.class);
                    intent.putExtra(C.FragmentTag.KEY_TAG, C.FragmentTag.PRODUCT_LIST);
                    startActivity(intent);
                }
            });

        } else {
            listBlankLayout.showContentView();
        }

        if (pageIndex == 1) {
            adapter.clear();
            adapter.addAll(datas);
        } else {
            adapter.addAll(datas);
        }
        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
            listView.setPullLoadEnable(false);
        }
    }


    @Override
    protected void onResponseMsgError() {
        super.onResponseMsgError();
        listView.setPullLoadEnable(false);
    }

    @Override
    protected void onRefreshDataViewCompleted() {
        super.onRefreshDataViewCompleted();
        onLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.redeemlist_iv_back_to_top:
                listView.smoothScrollToPosition(0);
        }
    }
}
