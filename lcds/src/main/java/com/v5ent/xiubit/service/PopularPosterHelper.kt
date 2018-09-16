package com.v5ent.xiubit.service

/**
 *
/**
 * 用于对所有的图片列表中图片选择操作进行统一处理
 * 由于使用的都是静态数据，在数据重新开始统计时记得调用init（）清零
*/
 * Created by hasee-pc on 2017/12/29.
 */
object PopularPosterHelper {
    var SelectImgUrl = ""

    fun initImageData() {
        SelectImgUrl = ""
    }

    fun synSelectImgUrl(imgUrl: String, isSelected: Boolean) {
        if (isSelected) SelectImgUrl = imgUrl
        else if (SelectImgUrl == imgUrl) {
            SelectImgUrl = ""
        }


    }

    fun isSelected(imgUrl: String): Boolean {
        return SelectImgUrl == imgUrl
    }

}