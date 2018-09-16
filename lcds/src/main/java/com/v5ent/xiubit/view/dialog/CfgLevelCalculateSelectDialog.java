package com.v5ent.xiubit.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.v5ent.xiubit.R;

import org.xsl781.data.BaseListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/5/24
 */

public class CfgLevelCalculateSelectDialog extends Dialog implements AdapterView.OnItemClickListener {

    private final String title;
    List<String> datas;
    @BindView(R.id.title)
    TextView mTitle;
    private Context ctx;
    @BindView(R.id.closedIv)
    ImageView mClosedIv;
    ItemClickListener mItemClickListener;
    @BindView(R.id.listView)
    ListView mListView;
    private static int selectType; //选择条目的数据类型
    public static final int TYPE_CFGLEVEL = 1;  //理财师等级选择
    public static final int TYPE_YEARPROFIT = 2; //年化收益选择

    public CfgLevelCalculateSelectDialog(@NonNull Context context, List<String> list, String title, int selectType,ItemClickListener listener) {
        super(context, com.toobei.common.R.style.customDialog);
        this.datas = list;
        ctx = context;
        this.title = title;
        mItemClickListener = listener;
        this.selectType = selectType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_cfp_level_calculate_select, null);
        setContentView(view);
        ButterKnife.bind(this);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mListView.setAdapter(new MyListAdapter(ctx, datas));
        mListView.setOnItemClickListener(this);
        mTitle.setText(title);
    }


    @OnClick({R.id.closedIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.closedIv:
                dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mItemClickListener.onItemClick(position);
        dismiss();
    }


    public interface ItemClickListener {

        void onItemClick(int itemIndex);

    }

    static class MyListAdapter extends BaseListAdapter<String> {
        public MyListAdapter(Context ctx) {
            super(ctx);
        }

        public MyListAdapter(Context ctx, List<String> datas) {
            super(ctx, datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootView;
            if (convertView == null) {
                rootView = inflater.inflate(R.layout.item_textview_calculate_select, null);
            } else {
                rootView = convertView;
            }
            String str = datas.get(position);
            if (str != null && selectType == TYPE_YEARPROFIT) {
//                str = new DecimalFormat("##0.0").format(Double.parseDouble(str.trim())) + "%";
                str = str.trim() + "%";
            }
            TextView textView = (TextView) rootView.findViewById(R.id.text);
            textView.setText(str);
            return rootView;
        }
    }
}
