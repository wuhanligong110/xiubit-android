package com.toobei.tb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.adapter.RewardDetailAdapter;
import com.toobei.tb.entity.RewardDetail;
import com.toobei.tb.entity.RewardDetailPageListEntity;

import org.xsl781.ui.xlist.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee-pc on 2017/2/15.
 */
public class RewardDetailFragment extends FragmentBase implements XListView.IXListViewListener {
    private String dataType; //收支类型(0=全部1=收入|2=支出)
    private ListBlankLayout blankLayout;
    private XListView lv;
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<RewardDetail> rewardDetailList = new ArrayList<>();
    private RewardDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.dataType = getArguments().getString("DateType");
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.my_list_blanklayout, null);
        blankLayout = (ListBlankLayout) rootView.findViewById(R.id.list_blank_layout);
        lv = (XListView) blankLayout.initContentView(R.layout.list_blank_xlistview_layout);
        initView();
        getNetdata(true);
        return rootView;
    }

    /**
     * 获取数据
     *
     * @param b
     */
    private void getNetdata(boolean b) {
        new MyNetAsyncTask(ctx, false) {

            private RewardDetailPageListEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().queryRewardDetailPageList(MyApp.getInstance().getLoginService().token, pageIndex + "", pageSize + "", dataType);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<RewardDetail> datas = response.getData().getDatas();
                        pageIndex = response.getData().getPageIndex();
                        if (pageIndex == 1) {
                            rewardDetailList.clear();
                            if (datas.size() == 0) {
                                if (isAdded()) {
                                    switch (dataType) {
                                        case "0":
                                            blankLayout.showBlankImageAndTextAndButton(R.drawable.img_no_data_blank, getResources().getString(R.string.blank_list_text_no_data_all_income), "去投资", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(ctx, MainActivity.class);
                                                    intent.putExtra("switchFragment",1);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case "1":
                                            blankLayout.showBlankImageAndTextAndButton(R.drawable.img_no_data_blank, getResources().getString(R.string.blank_list_text_no_data_Reward_detail_income), "去投资", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(ctx, MainActivity.class);
                                                    intent.putExtra("switchFragment",1);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case "2":
                                            blankLayout.showBlankImageAndTextAndButton(R.drawable.img_no_data_blank, getResources().getString(R.string.blank_list_text_no_data_Reward_detail_out), "去投资", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(ctx, MainActivity.class);
                                                    intent.putExtra("switchFragment",1);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        default:
                                            break;
                                    }

                                }
                                stopLoad();
                                return;
                            }
                        }
                        rewardDetailList.addAll(datas);
                        adapter.notifyDataSetChanged();
                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            lv.setPullLoadEnable(false);
                        } else {
                            lv.setPullLoadEnable(true);
                        }
                        pageIndex++;
                    } else {
                        if (isAdded()) ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    if (isAdded())
                        ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                stopLoad();
            }

        }.execute();
    }

    private void stopLoad() {
        if (lv != null) {
            lv.stopRefresh();
            lv.stopLoadMore();
        }
    }

    private void initView() {
        adapter = new RewardDetailAdapter(ctx, rewardDetailList, dataType);
        lv.setAdapter(adapter);
        lv.setDividerHeight(0);
        lv.setXListViewListener(this);
    }


    @Override
    public void onRefresh() {
        pageIndex = 1;
        getNetdata(true);
    }

    @Override
    public void onLoadMore() {
        getNetdata(true);
    }

}
