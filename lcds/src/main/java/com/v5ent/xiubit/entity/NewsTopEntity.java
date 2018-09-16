package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by yangLin on 2018/4/12.
 */

public class NewsTopEntity extends BaseEntity {
    private static final long serialVersionUID = 1873190116859131763L;
    public String creator;	//来源	string
    public String crtTime;	//时间	string
    public String img;		//string
    public String itemType;	//条目类型 1：每日财经早报 2:资讯	number
    public String linkUrl;	//	string
    public String name;		//string
    public String newsId;	//资讯ID	string
    public String readingAmount;	//浏览量	string
    public String shareIcon;	//分享icon	string
    public String summary;		//string
    public String title;	//标题	string
    public String typeName;		//string
}
