package com.toobei.common.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import com.toobei.common.R
import com.toobei.common.TopApp
import com.toobei.common.TopNetBaseActivity
import com.toobei.common.utils.BitmapUtil
import com.toobei.common.utils.CameraUtil
import com.toobei.common.utils.PathUtils
import kotlinx.android.synthetic.main.activity_top_card_scan.*
import org.xsl781.utils.Logger
import org.xsl781.utils.MD5Util
import java.io.File


/**
 * Created by yangLin on 2018/1/3.
 */
abstract class TopCardScanActivity : TopNetBaseActivity() {

    private var mCamera: Camera? = null
    private var mHolder: SurfaceHolder? = null
    private var mCameraId = 0
    protected var scanType = 0
    private var isShowPreview = false
    private var photoBitmap: Bitmap? = null

    override fun getContentLayout(): Int = R.layout.activity_top_card_scan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanType = intent.getIntExtra("ScanType", 0)
        initView()
    }


    private fun initView() {
        headerLayout.showTitle(when (scanType) {
            TopCardManagerAdd.TYPE_IDENTITY -> "拍摄身份证"
            TopCardManagerAdd.TYPE_BANKCARD -> "拍摄银行卡"
            else -> "拍摄识别"
        })
        scanRemindIv.setImageResource(when (scanType) {
            TopCardManagerAdd.TYPE_IDENTITY -> R.drawable.img_scan_remind_text_foridcard
            TopCardManagerAdd.TYPE_BANKCARD -> R.drawable.img_scan_remind_text_forbank
            else -> R.drawable.img_scan_remind_text_foridcard
        })

        //surface 实时预览
        mCamera = android.hardware.Camera.open(mCameraId)
        mHolder = surfaceV.holder
        mHolder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                mCamera?.stopPreview()
                startDisplay()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                releaseCamera()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                startDisplay()
            }
        })
        //底部按钮

        takePhotoIv.setOnClickListener {
            captrue()
        }//  拍照
        confimIv.setOnClickListener {
            saveBitmapAndUpData()
        }// 确定
        resetIv.setOnClickListener {
            showCamerView(true)
            startDisplay()
        }// 重置
        showCamerView(true)


    }

    private fun saveBitmapAndUpData() {
        val filename = MD5Util.MD5(System.currentTimeMillis().toString()) + ".jpg"

////            //放大图片
//        if (TopApp.displayWidth < 1080) {
//            val scaleRadio = 1080f / TopApp.displayWidth
//            photoBitmap = BitmapUtil.scaleImage(photoBitmap, scaleRadio, scaleRadio)
//        }
        //保存
        val file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, photoBitmap, false)
//        val uri = Uri.fromFile(file)
//        imagePath = file.absolutePath
////把文件插入到系统图库
//        MediaStore.Images.Media.insertImage(ctx.contentResolver, file.absolutePath, filename, null)
//        //保存图片后发送广播通知更新数据库
//        ctx.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
//        ToastUtil.showCustomToast("保存成功!")

        upData(file)


    }

    protected abstract fun upData(file: File)

    private fun showCamerView(show: Boolean) {
        isShowPreview = !show
        camerGroup.visibility = if (show) View.VISIBLE else View.GONE
        previewGroup.visibility = if (show) View.GONE else View.VISIBLE
        camerBtnGroup.visibility = if (show) View.VISIBLE else View.GONE
        previewBtnGroup.visibility = if (show) View.GONE else View.VISIBLE

    }

    private fun captrue() {
        mCamera?.takePicture(null, null, Camera.PictureCallback { data, camera ->
            //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            var saveBitmap = CameraUtil.getInstance().setTakePicktrueOrientation(mCameraId, bitmap)
            Logger.e("camer,H：" + saveBitmap.height + ",w:" + saveBitmap.width)
            previewIv.setImageBitmap(saveBitmap)
            showCamerView(false)
            photoBitmap = saveBitmap

        })
    }

    /**
     * 设置
     */
    private fun setupCamera(camera: Camera) {
        val parameters = camera.parameters

        if (parameters.supportedFocusModes.contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        }
        val surfaceWidth = if (surfaceV.width == 0) TopApp.displayWidth else surfaceV.width
        val surfaceHeight = if (surfaceV.height == 0) TopApp.displayHeight else surfaceV.height
        Logger.d("cardScan", "surfaceV==" + surfaceWidth + ":" + surfaceHeight)
        //这里第三个参数为最小尺寸 getPropPreviewSize方法会对从最小尺寸开始升序排列 取出所有支持尺寸的最小尺寸
        val previewSize = CameraUtil.getInstance().getMatchSize(parameters.supportedPreviewSizes, surfaceHeight, TopApp.displayWidth)
        parameters.setPreviewSize(previewSize.width, previewSize.height)
        Logger.d("cardScan", "previewV==" + previewSize.width + ":" + previewSize.height)
        val pictrueSize = CameraUtil.getInstance().getMatchSize(parameters.supportedPictureSizes, surfaceHeight, TopApp.displayWidth)
        parameters.setPictureSize(pictrueSize.width, pictrueSize.height)
        Logger.d("cardScan", "pictrueV==" + pictrueSize.width + ":" + pictrueSize.height)
        camera.parameters = parameters
        camera.cancelAutoFocus()
    }


    override fun onResume() {
        super.onResume()
        startDisplay()
    }

    private fun startDisplay() {
        if (isShowPreview) return
        if (mCamera == null) mCamera = android.hardware.Camera.open(mCameraId)
        if (mHolder == null) return
        setupCamera(mCamera!!)
        mCamera?.setPreviewDisplay(mHolder);
        //亲测的一个方法 基本覆盖所有手机 将预览矫正
        CameraUtil.getInstance().setCameraDisplayOrientation(this, mCameraId, mCamera);
        mCamera?.startPreview()

    }

    override fun onPause() {
        super.onPause()
        releaseCamera()
    }


    /**
     * 释放相机资源
     */
    private fun releaseCamera() {
        mCamera?.setPreviewCallback(null)
        mCamera?.stopPreview()
        mCamera?.release()
        mCamera = null
    }
}