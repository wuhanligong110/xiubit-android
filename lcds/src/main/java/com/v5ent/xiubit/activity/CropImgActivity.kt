package com.v5ent.xiubit.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.isseiaoki.simplecropview.callback.CropCallback
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.activity_crop_img.*
import org.greenrobot.eventbus.EventBus
import java.io.File

/**
 * //用于头像裁剪
 * Created by hasee-pc on 2017/12/25.
 */
class CropImgActivity : NetBaseActivity() {
    var imagePath = ""
    override fun getContentLayout(): Int = R.layout.activity_crop_img

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imagePath = intent.getStringExtra("imagePath")
        initView()
    }

    private fun initView() {
        headerLayout.showTitle("裁剪图片")
        cropImageView.load(Uri.fromFile(File(imagePath))).execute(null)
        cropImageView.setOutputMaxSize(200,200)

        cancelTv.setOnClickListener { finish() }
        confirmTv.setOnClickListener { cropImageView.cropAsync(object : CropCallback{
            override fun onError(e: Throwable?) {
                ToastUtil.showCustomToast("裁剪失败，请重新裁剪")
            }

            override fun onSuccess(cropped: Bitmap?) {
                EventBus.getDefault().post(CropEvent(cropped))
//                ToastUtil.showCustomToast("裁剪成功")
                finish()
            }
        }) }
    }

    companion object {
        class CropEvent(val bitmap : Bitmap? = null)
    }
}