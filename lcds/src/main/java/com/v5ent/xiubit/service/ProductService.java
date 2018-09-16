package com.v5ent.xiubit.service;

import com.toobei.common.entity.ProductDetail;
import com.v5ent.xiubit.data.ProductListAdapter;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ActivityStack;

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
    public static void addProductListAndAdapter(List<ProductDetail> list, ProductListAdapter adapter) {

        if (!allProductListAndAdapter.containsKey(adapter)) {
            allProductListAndAdapter.put(adapter, list);
        }

    }

    /**
     * 刷新界面
     *
     * @param productDetail 进行了推荐或者取消推荐的产品
     */
    public static void notifyUiAndData(final ProductDetail productDetail) {

        new Thread() {
            @Override
            public void run() {
                String cfpRecommend = productDetail.getCfpRecommend();
                Set<Map.Entry<BaseListAdapter<ProductDetail>, List<ProductDetail>>> entries = allProductListAndAdapter.entrySet();
                for (Map.Entry<BaseListAdapter<ProductDetail>, List<ProductDetail>> entry : entries) {
                    final List<ProductDetail> list = entry.getValue();
                    final BaseListAdapter<ProductDetail> adapter = entry.getKey();
                    for (ProductDetail product : list) {
                        if (productDetail.getProductId().equals(product.getProductId())) {
                            // 如果在集合中找到推荐状态变化了的产品就刷新ListView
                            product.setCfpRecommend(cfpRecommend);
                          //  ToastUtil.showCustomToast("找到" + product.getProductName() + "推荐状态==" + product.getCfpRecommend() + "点击" + productDetail.getProductName() + "推荐状态==" + productDetail.getCfpRecommend());
                            ActivityStack.getInstance().topActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 移除不需要同步产品推荐状态的ListView
     *
     * @param adapter
     */
    public static void removeList(ProductListAdapter adapter) {
        if (adapter != null) {
            allProductListAndAdapter.remove(adapter);
            adapter.activity = null; //避免内存泄漏
        }


    }
}
