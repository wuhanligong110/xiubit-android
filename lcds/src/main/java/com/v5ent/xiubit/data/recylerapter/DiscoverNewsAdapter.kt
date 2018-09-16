package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.PageListBase
import com.toobei.common.entity.ShareContent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.WebActivityCommon
import com.v5ent.xiubit.entity.NewsPageEntity
import com.v5ent.xiubit.network.httpapi.GrowthHandBookApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yangLin on 2018/4/12.
 */
class DiscoverNewsAdapter : BaseQuickAdapter<NewsPageEntity,BaseViewHolder>(R.layout.item_growth_manual){
    override fun convert(helper: BaseViewHolder, item: NewsPageEntity) {
        val position = helper.getAdapterPosition()
        helper.setText(R.id.titleTv, item.title)
                .setText(R.id.dateTv,item.crtTime)
                .setText(R.id.sourceTv,item.creator)
                .setText(R.id.readNumTv, "" + item.readingAmount + "人浏览")
                .setVisible(R.id.footDividView, position == itemCount - 1)
        PhotoUtil.loadImageByGlide(mContext, item.img, helper.getView<View>(R.id.imageIv) as ImageView)
        helper.getView<View>(R.id.rootView).setOnClickListener {
            val intent = Intent(mContext, WebActivityCommon::class.java)
            val url: String
            if (TextUtils.isEmpty(item.linkUrl)) {
                url = C.URL_INFORMATIONDETAILURL + "?id="+item.newsId +"&type="+item.itemType
            } else {
                url = item.linkUrl
                notionBackHasRead(item.newsId)
            }
            val shareContent = ShareContent(item.title, item.summary, url, item.shareIcon)
            intent.putExtra("shareContent", shareContent)
            intent.putExtra("url", url)
            mContext.startActivity(intent)
        }
    }

    fun notionBackHasRead(id:String){
        RetrofitHelper.getInstance().retrofit.create(GrowthHandBookApi::class.java)
                .newsDetail(ParamesHelp()
                        .put("newsId", "$id")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<PageListBase<BaseEntity>>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<PageListBase<BaseEntity>>) {

                    }

                })
    }
}