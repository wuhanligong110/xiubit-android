package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.utils.PinyinUtils;
import com.toobei.common.view.timeselector.Utils.TextUtil;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/25
 */

public class CfgListData extends BaseEntity {
    private static final long serialVersionUID = 669891235718992598L;


    public String headImage;
    public String mobile;
    public String registTime;
    public String teamMemberCount;
    public String userName;
    public String userId;

    public String getFirstLetter() {

        String pinyin = PinyinUtils.getPinyin(userName);
        if (!TextUtil.isEmpty(pinyin)) return pinyin.substring(0,1);
        else return "#";

    }
}
