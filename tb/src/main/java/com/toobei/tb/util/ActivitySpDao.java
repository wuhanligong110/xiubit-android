package com.toobei.tb.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.xsl781.utils.Logger;

import static com.toobei.tb.util.DynamicSkipManage.SKIPTAG_PRODUCT_LIST;

/**
 * 公司: tophlc
 * 类说明:用于保存对应activity的键值对，用于浏览器跳转页面的标识
 *
 * @author yangLin
 * @time 2017/6/7
 */

public class ActivitySpDao {
    private volatile static ActivitySpDao activitySpDao;  //当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。
    private Context cxt;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ActivitySpDao(Context cxt) {
        this.cxt = cxt;
        pref = cxt.getSharedPreferences("activitySkip", Context.MODE_PRIVATE);
        editor = pref.edit();
        Logger.d("PrefDao init no specific user");
    }

    public static ActivitySpDao getInstance(Context ctx) {
        if (activitySpDao == null) {
            synchronized (ActivitySpDao.class) {
                if (activitySpDao == null)
                    activitySpDao = new ActivitySpDao(ctx);
            }
        }
        return activitySpDao;
    }

    public void init(){
        editor.putInt("ProductList",SKIPTAG_PRODUCT_LIST);
        editor.apply();
    }

    public int getValue(String key){
        return pref.getInt(key,-1);
    }
}
