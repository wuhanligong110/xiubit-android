package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.webkit.JavascriptInterface
import com.nanchen.compresshelper.CompressHelper
import com.toobei.common.TopApp
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.ImageResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.service.TopAppObject
import com.toobei.common.utils.*
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.SunburnDetailEntity
import com.v5ent.xiubit.network.httpapi.APartApi
import com.v5ent.xiubit.util.DynamicSkipManage
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_show_form.*
import org.xsl781.utils.Logger
import org.xsl781.utils.MD5Util
import java.io.File

/**
 * Created by 11191 on 2018/6/7.
 */
class ShowFormActivity : NetBaseActivity() {
    var id = ""
    var url = ""
    override fun getContentLayout(): Int = R.layout.activity_show_form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra("id")
        url = intent.getStringExtra("url")
        initView()
        queryFormData()
    }


    private fun initView() {
        headerLayout.showTitle("晒单")
        id = intent.getStringExtra("id") ?: ""

//        titleTv.text = "我在${investTime}投资网投网${productDeadLine}天标 ${investAmt}元获得${feeAmt}元理财返现"
        showBtn.setOnClickListener { cutImage(true) }

        webView.setAppObject(MyTopAppObject())
        webView.loadUrl(url)

        clickV.setOnClickListener({
            val cutImageFile = cutImage()
            var intent = Intent(ctx,PopuImgViewActivity::class.java)
            intent.putExtra("path",cutImageFile.absolutePath)
            startActivity(intent)
        })
    }

    private fun queryFormData() {
        RetrofitHelper.getInstance().retrofit.create(APartApi::class.java)
                .querySunburn(ParamesHelp()
                        .put("id", id)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SunburnDetailEntity>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SunburnDetailEntity>) {
                        var investTime = response.data.investTime
                        if (!TextUtil.isEmpty(investTime) && investTime.length >= 10){
                            investTime = investTime.substring(0,10)
                        }
                        var text = "我在${investTime}投资网投网${response.data.productDeadLine}标${response.data.investAmt}元获得额外${response.data.feeAmt}元理财返现"
                        TextDecorator.decorate(titleTv,text).setTextColor(R.color.text_red_common,"获得额外${response.data.feeAmt}元理财返现").build()
                    }

                })
    }


    private fun cutImage(isUpLoad : Boolean = false) : File{
        val bm = ScreenShotUtils.shot(ctx, webView)
        val filename = MD5Util.MD5(System.currentTimeMillis().toString() + "")
        val file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, bm, false)

        //图片不能大于2M,超过的进行压缩
        var upfile = file
        Logger.d("压缩前\${file.length().toFloat() / 1024 / 1024}M")
        if (upfile.length() > 1020 * 1020) {  //大于1M的图片进行压缩
            try {
                upfile = CompressHelper.Builder(ctx)
                        .setMaxWidth(1080f)
                        .setMaxHeight(1920f)
                        .setQuality(100)
                        .build().compressToFile(file)
                Logger.d("压缩后\${upfile.length().toFloat() / 1024 / 1024}M")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        if (isUpLoad) {
            object : MyNetAsyncTask(ctx, false) {
                internal var response: ImageResponseEntity? = null

                @Throws(Exception::class)
                override fun doInBack() {
                    response = TopApp.getInstance().httpService.personcenterUploadImageFile(upfile)
                }

                override fun onPost(e: Exception?) {
                    if (response?.info?.md5.isNullOrBlank()) {
                        ToastUtil.showCustomToast(e.toString())
                    } else {
                        showForm(response?.info?.md5!!)
                    }
                }
            }.execute()
        }
        return upfile
    }


    private fun showForm(imgMd5: String) {
        RetrofitHelper.getInstance().retrofit.create(APartApi::class.java)
                .sunburn(ParamesHelp()
                        .put("id", id)
                        .put("image", imgMd5).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {}

                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        if ("0" == response.code) {
                            ToastUtil.showCustomToast("晒单成功，奖励金 +0.2元")
                            finish()
                        }
                    }

                })
    }

    inner class MyTopAppObject : TopAppObject() {


        /**
         * token失效
         */
        @JavascriptInterface
        fun tokenExpired() {
            TopApp.loginAndStay = true
            TopApp.getInstance().logOutEndNoSikp()
            skipActivity(ctx, LoginActivity::class.java)
        }


        @JavascriptInterface
        fun jumpToNativePage(json: String) {
            val str = String(Base64.decode(json.toByteArray(), Base64.DEFAULT))
            DynamicSkipManage.skipActivity(ctx, str)
        }
    }
}