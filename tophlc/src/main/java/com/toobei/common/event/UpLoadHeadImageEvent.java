package com.toobei.common.event;

/**
 * 上传用户头像
 *
 * @author Administrator
 * @time 2016/11/22 0022 下午 3:36
 */
public class UpLoadHeadImageEvent {
    public String imgPath;

    public UpLoadHeadImageEvent(String imgPath) {
        this.imgPath = imgPath;
    }
}
