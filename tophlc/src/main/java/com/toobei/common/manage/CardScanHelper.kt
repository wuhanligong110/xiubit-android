package com.toobei.common.manage

import com.nanchen.compresshelper.CompressHelper
import com.toobei.common.TopApp
import com.toobei.common.activity.TopCardManagerAdd
import com.toobei.common.entity.BankcardRecoData
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.IdcardRecoData
import com.toobei.common.event.CardScanEvent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardUpRecoApi
import com.toobei.common.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.xsl781.utils.Logger
import java.io.File

/**
 * Created by yangLin on 2018/1/7.
 */
object CardScanHelper {
    interface CallBack {
        fun onSuccess()
        fun onFailed()
    }


    fun scan(file: File, scanType: Int, callBack: CallBack?) {
        //图片不能大于2M,超过的进行压缩
        var upfile = file
        Logger.d("压缩前${file.length().toFloat() / 1024 / 1024}M")
        if (upfile.length() > 1020 * 1020) {  //大于1M的图片进行压缩
            try {
                upfile = CompressHelper.Builder(TopApp.getInstance().applicationContext)
                        .setMaxWidth(1080f)
                        .setMaxHeight(1920f)
                        .setQuality(100)
                        .build().compressToFile(file)
                Logger.d("压缩后${upfile.length().toFloat() / 1024 / 1024}M")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //上传
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), upfile)
        //后台定义的key 是"file" 不能变
        val body = MultipartBody.Part.createFormData("file", upfile.getName(), requestBody);
        when (scanType) {
            TopCardManagerAdd.TYPE_IDENTITY -> {
                RetrofitHelper.getInstance().noApiRetrofit.create(CardUpRecoApi::class.java)
                        .uploadIdcardReco(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : NetworkObserver<BaseResponseEntity<IdcardRecoData>>() {
                            override fun bindViewWithDate(response: BaseResponseEntity<IdcardRecoData>) {
                                if (response.data.idCard.isNullOrBlank() || response.data.name.isNullOrBlank()) {
                                    ToastUtil.showCustomToast(response.data.errorMsg)
                                    callBack?.onFailed()
                                } else {
                                    EventBus.getDefault().post(CardScanEvent(response.data))
                                    callBack?.onSuccess()
                                }
                            }

                            override fun onNetSystemException(error: String?) {
                                super.onNetSystemException(error)
                                callBack?.onFailed()
                            }

                        })
            }
            TopCardManagerAdd.TYPE_BANKCARD -> {
                RetrofitHelper.getInstance().noApiRetrofit.create(CardUpRecoApi::class.java)
                        .uploadBankcardReco(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : NetworkObserver<BaseResponseEntity<BankcardRecoData>>() {
                            override fun bindViewWithDate(response: BaseResponseEntity<BankcardRecoData>) {
                                if (response.data.bankCard.isNullOrBlank()) {
                                    ToastUtil.showCustomToast(response.data.errorMsg)
                                    callBack?.onFailed()
                                } else {
                                    EventBus.getDefault().post(CardScanEvent(null, response.data))
                                    callBack?.onSuccess()
                                }
                            }

                            override fun onNetSystemException(error: String?) {
                                super.onNetSystemException(error)
                                callBack?.onFailed()
                            }

                        })
            }
        }
    }
}