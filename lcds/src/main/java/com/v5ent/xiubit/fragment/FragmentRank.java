package com.v5ent.xiubit.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.RankWeeklyCommissionAdapter;
import com.v5ent.xiubit.entity.RankWeeklyCommission;
import com.v5ent.xiubit.entity.RankWeeklyCommsionDatasEntity;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.ui.xlist.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static com.toobei.common.utils.ToastUtil.showCustomToast;

/**
 * 公司: tophlc
 * 类说明:  Fragment-周佣榜
 *
 * @author qingyechen
 * @time 2017/2/23 14:15
 */
@SuppressLint("ValidFragment")
public class FragmentRank extends FragmentBase implements IXListViewListener {


    private final String month;
    private int rankType = 0;//0 收益榜; 1 : leader 奖励排行榜
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tipsLL)
    View tipsLL;
    @BindView(R.id.text_tips)
    TextView textTips;
    private int pageIndex = 1;


    public RankWeeklyCommissionAdapter adapter;

    public List<RankWeeklyCommission> rankList = new ArrayList<>();
    private int pageSize = 10;

    public FragmentRank(int rankType,String month) {
        this.rankType = rankType;
        this.month = month;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rank,null);
        ButterKnife.bind(this, rootView);
        initView();
        getData(true);
        return rootView;
    }

    private void initView() {
        tipsLL.setVisibility(MyApp.getInstance()
                .getCurUserSp()
                .getIsShowInComeRankTips() ? GONE : View.VISIBLE);
        
        adapter = new RankWeeklyCommissionAdapter(getContext(), rankList, rankType,month);
        listView.setAdapter(adapter);
        listView.setDividerHeight(0);
        listView.setXListViewListener(this);
    }





    private void getData(boolean openDialog) {
        new MyNetAsyncTask(getActivity(), openDialog) {
            RankWeeklyCommsionDatasEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = rankType == 0 ? MyApp.getInstance()
                        .getHttpService()
                        .getRankWeeklyCommission(pageIndex,pageSize) : MyApp.getInstance()
                        .getHttpService()
                        .getRankLeaderCommission(pageIndex,pageSize);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        if (pageIndex == 1) {
                            listView.setAutoLoadMore(true);
                            listView.setPullLoadEnable(true);
                            rankList.clear();
                        }
                        List<RankWeeklyCommission> datas = response.getData().getDatas();
                        pageIndex = response.getData().getPageIndex();
                        rankList.addAll(datas);
                        adapter.notifyDataSetChanged();

                        if (response.getData().getPageCount() <= response.getData()
                                .getPageIndex() || response.getData().getPageCount() <= 1) {
                            listView.setPullLoadEnable(false);
                        } else {
                            listView.setPullLoadEnable(true);
                        }

                        pageIndex++;
                    } else {
                        if (isAdded())
                            ToastUtil.showCustomToast(getActivity(), response.getErrorsMsgStr());
                    }
                } else {
                    if (isAdded())
                        showCustomToast(getActivity(), getString(R.string.pleaseCheckNetwork));
                }
                stopLoad();

            }
        }.execute();
    }



    @Override
    public void onRefresh() {

        //  loadData(false);
        pageIndex = 1;
//        getData(true);
        getData(true);

//        if (rankType == 1) {
//            getLeaderProfitStatus();
//        }
    }


    @Override
    public void onLoadMore() {
        getData(false);
    }

    public void stopLoad() {
        if (listView != null) {
            listView.stopRefresh();
            listView.stopLoadMore();
        }

    }

    

    @OnClick({R.id.img_close})
    public void onClick(View v) {
        switch (v.getId()) {
            
            case R.id.img_close:
                tipsLL.setVisibility(GONE);
                if (rankType == 0) {
                    MyApp.getInstance().getCurUserSp().setIsShowInComeRankTips(true);
                }
                break;

        }

    }
}
