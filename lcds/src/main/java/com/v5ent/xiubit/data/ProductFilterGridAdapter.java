package com.v5ent.xiubit.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.v5ent.xiubit.R;

import org.xsl781.data.BaseListAdapter;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/24
 */

public class ProductFilterGridAdapter extends BaseListAdapter<String> {
    private int selectPosition;
    private OnItemSelectedCallBack onItemSelectedCallBack;
    private String type;  //产品分类的筛选类型

    public ProductFilterGridAdapter(Context ctx, String[] typeVlueArrs, String type, int selectPosition,OnItemSelectedCallBack onItemSelectedCallBack) {
        super(ctx);
        this.type = type;
        for (String value : typeVlueArrs) {
            datas.add(value);
        }
        this.selectPosition = selectPosition;
        this.onItemSelectedCallBack = onItemSelectedCallBack;
    }

    private ProductFilterGridAdapter(Context ctx, List<String> datas) {
        super(ctx, datas);
        this.type = type;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_product_filter_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(datas.get(position));
        if (position == selectPosition) {
            viewHolder.tv.setSelected(true);
        } else {
            viewHolder.tv.setSelected(false);
        }

        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.tv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (position != selectPosition) {
                    selectPosition = position;
                    onItemSelectedCallBack.onItemSelected(type, position);
                    notifyDataSetChanged();
                }

        }});
        return convertView;
    }

    class ViewHolder {
        TextView tv;

        ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.tv);

        }

    }

    public void setOnItemSelectedCallBack(OnItemSelectedCallBack callBack) {
        this.onItemSelectedCallBack = callBack;
    }

    public interface OnItemSelectedCallBack {

        void onItemSelected(String type, int position);
    }


}
