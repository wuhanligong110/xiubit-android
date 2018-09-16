package com.toobei.common.utils;

/**
 * 公司: tophlc
 * 类说明：更新视图接口
 * @date 2015-12-15
 */
public interface UpdateViewCallBack<T> {
	void updateView(Exception e, T object);
}
