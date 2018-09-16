package com.toobei.tb.service;


import com.toobei.common.entity.ProductDetail;

import org.xsl781.data.BaseListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用于同步不同listView中的相同产品的推荐状态(本地刷新)
 */
public class ProductService {

    private static final String TAG = "ProductService";
    private static ProductService service = null;
    /**
     * 用于保存 数据集合和对应的适配器
     */
    private static HashMap<BaseListAdapter<ProductDetail>, List<ProductDetail>> allProductListAndAdapter = new HashMap<BaseListAdapter<ProductDetail>, List<ProductDetail>>();

    protected ProductService() {
        service = this;
    }

    public static synchronized ProductService getInstance() {
        if (service == null) {
            service = new ProductService();
        }
        return service;

    }

    /**
     * 把list数据 和对应的adapter 保存到 作为键值对保存到Map中
     *
     * @param list
     * @param adapter
     */
    @SuppressWarnings("unchecked")
    public static void addProductListAndAdapter(List<ProductDetail> list, BaseListAdapter<ProductDetail> adapter) {

        if (!allProductListAndAdapter.containsKey(adapter)) {
            allProductListAndAdapter.put(adapter, list);
        }

    }

    /**
     * 刷新界面
     *
     * @param productDetail 进行了推荐或者取消推荐的产品
     */
    public static void notifyUiAndData(ProductDetail productDetail) {
        String cfpRecommend = productDetail.getCfpRecommend();
        Set<Map.Entry<BaseListAdapter<ProductDetail>, List<ProductDetail>>> entries = allProductListAndAdapter.entrySet();
        for (Map.Entry<BaseListAdapter<ProductDetail>, List<ProductDetail>> entry : entries) {
            List<ProductDetail> list = entry.getValue();
            BaseListAdapter<ProductDetail> adapter = entry.getKey();
            for (ProductDetail product : list) {
                if (productDetail.getProductId().equals(product.getProductId())) {
                    product.setCfpRecommend(cfpRecommend.equals("1") ? "0" : "1"); // 如果在集合中找到就刷新ListView
                    // ToastUtil.showCustomToast("找到" + product.getProductName() + "推荐状态==" + product.getCfpRecommend() + "点击" + productDetail.getProductName() + "推荐状态==" + productDetail.getCfpRecommend());
                    // adapter.refresh(list);
                    adapter.notifyDataSetChanged();

                }
            }
        }
    }

    /**
     * 移除不需要同步产品推荐状态的ListView
     *
     * @param list
     */
    public static void removeList(List<ProductDetail> list) {
        allProductListAndAdapter.remove(list);
    }
}
