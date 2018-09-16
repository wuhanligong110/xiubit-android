package com.toobei.tb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.entity.ProductClassifyStatisticsDetail;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.tb.R;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

/**
 * 3.4.7 产品分类统计 v2.0.1
 *
 * @author Administrator
 * @time 2016/11/24 0024 上午 11:01
 */
public class ProductClassifyStatisticsAdapter extends BaseListAdapter<ProductClassifyStatisticsDetail> {

    private OnItemClickListener onItemClickListener;

    public ProductClassifyStatisticsAdapter(Activity ctx, List<ProductClassifyStatisticsDetail> datas) {
        super(ctx, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {


        if (null == convertView) {
            convertView = convertView.inflate(ctx, R.layout.item_product_classify, null);
        }
        ImageView cateIV = ViewHolder.findViewById(convertView, R.id.iv_product_cate_logo);
        TextView nameTV = ViewHolder.findViewById(convertView, R.id.text_product_cate_name);
        TextView desTV = ViewHolder.findViewById(convertView, R.id.text_product_cate_declare);
        TextView rateTV = ViewHolder.findViewById(convertView, R.id.text_product_cate_rate);
        TextView countTV = ViewHolder.findViewById(convertView, R.id.text_product_cate_count);

        final ProductClassifyStatisticsDetail data = datas.get(position);
        PhotoUtil.loadImageByGlide(ctx, data.getCateLogoInvestor() + "?f=png", cateIV);
        nameTV.setText(data.getCateName());
        desTV.setText(data.getCateDeclare());
        rateTV.setText(data.getFlowMinRateStatistics() + "~" + data.getFlowMaxRateStatistics());
        countTV.setText(data.getCount() + "个");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemclick(position);
            }
        });
        return convertView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void onItemclick(int position);
    }

}
