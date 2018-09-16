package com.v5ent.xiubit.activity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.service.ShareService
import com.toobei.common.utils.BitmapUtil
import com.toobei.common.utils.PathUtils
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.PopularPosterPageAdapter
import com.v5ent.xiubit.entity.PopularPosterTypeData
import com.v5ent.xiubit.network.httpapi.PopularPosterApi
import com.v5ent.xiubit.network.httpapi.QrCodeApi
import com.v5ent.xiubit.service.PopularPosterHelper
import com.v5ent.xiubit.util.ParamesHelp
import com.way.util.DisplayUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.popular_poster_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.xsl781.utils.MD5Util

/**
 * Created by hasee-pc on 2017/12/28.
 */
class PopularPosterActivity : NetBaseActivity() {
    lateinit var mAdapter: PopularPosterPageAdapter

    companion object {
        var categoryList = arrayListOf<PopularPosterTypeData.TypeListBean>()

        fun initCategoryList(callBack: PopularPosterActivity.Companion.CallBack? = null) {
            RetrofitHelper.getInstance().retrofit.create(PopularPosterApi::class.java)
                    .getPopularPosterType(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<BaseResponseEntity<PopularPosterTypeData>>(){
                        override fun bindViewWithDate(response: BaseResponseEntity<PopularPosterTypeData>) {
                            categoryList.clear()
                            categoryList.addAll(response.data.typeList)
                            callBack?.onSuccess()
                        }

                        override fun onNetSystemException(error: String?) {
                            super.onNetSystemException(error)
                            ToastUtil.showCustomToast(MyApp.getInstance().getString(com.toobei.common.R.string.pleaseCheckNetwork))
                            callBack?.onError()
                        }

                    })

        }

        interface CallBack{
            fun onSuccess()
            fun onError()
        }


    }

    override fun getContentLayout(): Int = R.layout.popular_poster_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        headerLayout.showTitle("推广海报")
        PopularPosterHelper.initImageData()

        fun initVpage() {
            var tabWidth = (MyApp.displayWidth - DisplayUtil.dip2px(ctx, 30f)).toFloat() / 4
            tabLayout.tabWidth = DisplayUtil.px2dip(ctx, tabWidth).toFloat()

            mAdapter = PopularPosterPageAdapter(ctx, supportFragmentManager, PopularPosterActivity.categoryList)
            vPager.adapter = mAdapter
//            vPager.offscreenPageLimit = 5
            tabLayout.setViewPager(vPager)
        }

        if (PopularPosterActivity.categoryList != null && PopularPosterActivity.categoryList.size > 0) initVpage()
        else PopularPosterActivity.initCategoryList(object : PopularPosterActivity.Companion.CallBack {
            override fun onSuccess() {
                initVpage()
            }

            override fun onError() {
                showLoadFail()
            }
        })

        initShareEvent()
    }

    override fun onLoadFailRefresh() {
        super.onLoadFailRefresh()
        initView()
    }

    private fun initShareEvent() {
        shareWXfriendEntry.setOnClickListener { makeShare(0) }
        shareWXcircleEntry.setOnClickListener { makeShare(1) }
    }

    /**
     * @param tag 0: 分享微信好友 1： 分享微信朋友圈
     */
    private fun makeShare(tag: Int) {
        shareWXfriendEntry.isEnabled = false
        shareWXcircleEntry.isEnabled = false
        showLoadProgress(true)

        RetrofitHelper.getInstance().retrofit.create(QrCodeApi::class.java)
                .getQrcode(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .map { response ->
                    val qrcode1Url = response.data.url
                    val selectImgUrl = PopularPosterHelper.SelectImgUrl
                    val filename = MD5Util.MD5(selectImgUrl + System.currentTimeMillis())
                    val url = MyApp.getInstance().httpService.getImageUrlFormMd5(selectImgUrl)

                    val imageBitmap = BitmapUtil.getBitmapFromUrl(ctx, url)  //海报
                    val qrcodeBitmap = BitmapUtil.getBitmapFromUrl(ctx, qrcode1Url)  //二维码
                    val mergeBitmap = mergeBitmap(imageBitmap, qrcodeBitmap)
                    val file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, mergeBitmap, false)
                    file
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ file ->

                    val shareService = ShareService()
                    when (tag) {
                        0 -> shareService.shareImageToWeixinFriend(file.absolutePath)
                        1 -> shareService.shareImageToWeixinCircle(file.absolutePath)
                    }

                }, {
                    //错误
                    ToastUtil.showCustomToast("分享失败，请重试")
                    showLoadContent()
                    shareWXfriendEntry.isEnabled = true
                    shareWXcircleEntry.isEnabled = true
                }, {
                    //完成
                    showLoadContent()
                    shareWXfriendEntry.isEnabled = true
                    shareWXcircleEntry.isEnabled = true
                })

    }

    /**
     * @param firstBitmap  底下的
     * @param secondBitmap 面上的
     * @return
     */
    private fun mergeBitmap(firstBitmap: Bitmap, secondBitmap: Bitmap): Bitmap {
        val bottomBitmap = Bitmap.createBitmap(firstBitmap)
        val topBitmap = BitmapUtil.scaleImageSide(secondBitmap, 180, 180)
        val canvas = Canvas(bottomBitmap)
        canvas.drawBitmap(bottomBitmap, Matrix(), null)
        canvas.drawBitmap(topBitmap, ((bottomBitmap.width - topBitmap.width) / 2).toFloat(), (bottomBitmap.height - topBitmap.height - 120).toFloat(), null)
        return bottomBitmap
    }


    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshState(event: PopularPosterHelper) {
        showShareView(!event.SelectImgUrl.isNullOrBlank())
    }

    var shareViewIsVisib = false

    fun showShareView(isShow: Boolean) {
        val enterAnimation = AnimationUtils.loadAnimation(ctx, R.anim.push_bottom_in)
        val outAnimation = AnimationUtils.loadAnimation(ctx, R.anim.push_bottom_out)

        enterAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                shareView.visibility = View.VISIBLE
                shareViewIsVisib = true
            }

            override fun onAnimationEnd(animation: Animation?) {}

        })

        outAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                shareView.visibility = View.GONE
                shareViewIsVisib = false
            }

        })
        if (isShow) {
            if (!shareViewIsVisib) shareView.startAnimation(enterAnimation)

        } else {
            if (shareViewIsVisib) shareView.startAnimation(outAnimation)
        }
    }

}