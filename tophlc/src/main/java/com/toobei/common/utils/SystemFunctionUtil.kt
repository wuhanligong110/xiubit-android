package com.toobei.common.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.toobei.common.R
import com.toobei.common.TopApp
import com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL
import com.toobei.common.view.dialog.PromptDialogCalTel
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import org.xsl781.utils.Logger
import java.io.File


/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/13
 */

object SystemFunctionUtil {


    fun CallServicePhone(activity: Activity, phoneNum: String?) {
        if (TextUtils.isEmpty(phoneNum)) return
        AndPermission.with(activity)
                .requestCode(MY_PERMISSIONS_REQUEST_DIAL)
                .permission(Manifest.permission.CALL_PHONE)
                .rationale { requestCode, rationale -> }
                .callback(object : PermissionListener {
                    override fun onSucceed(requestCode: Int, grantPermissions: List<String>) {
                        var serviceTelephone = TopApp.getInstance().defaultSp.serviceTelephone
                        if (TextUtil.isEmpty(serviceTelephone)) serviceTelephone = "400-888-6987"
                        val dialog = PromptDialogCalTel(activity, true, "致电$serviceTelephone？", phoneNum)
                        dialog.setBtnPositiveColor(R.color.text_blue_common)
                        dialog.show()
                    }

                    override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {
                        Logger.e("用户拒绝打电话权限")
                    }
                })
                .start()
    }

    fun CallNormerPhone(activity: Activity, phoneNum: String?) {
        if (TextUtils.isEmpty(phoneNum)) return
        AndPermission.with(activity)
                .requestCode(MY_PERMISSIONS_REQUEST_DIAL)
                .permission(Manifest.permission.CALL_PHONE)
                .rationale { requestCode, rationale -> }
                .callback(object : PermissionListener {
                    override fun onSucceed(requestCode: Int, grantPermissions: List<String>) {
                        val dialog = PromptDialogCalTel(activity, false, "确认电话?", phoneNum)
                        dialog.setBtnPositiveColor(R.color.text_blue_common)
                        dialog.show()
                    }

                    override fun onFailed(requestCode: Int, deniedPermissions: List<String>) {
                        Logger.e("用户拒绝打电话权限")
                    }
                })
                .start()
    }


    const val CAMERA_REQUEST_CODE: Int = 666
    const val ALBUM_REQUEST_CODE: Int = 777

    fun takePhotoFromCamera(ctx: Activity, imgPhth: String = PathUtils.getImagePath() + System.currentTimeMillis() + ".png") {
            val intent = Intent()
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri = Uri.fromFile(File(imgPhth))
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            ctx.startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }


        fun openPhotoAlbumFromAlbum(ctx: Activity) {
            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            intent.action = Intent.ACTION_GET_CONTENT
            ctx.startActivityForResult(intent, ALBUM_REQUEST_CODE)
        }
    }
