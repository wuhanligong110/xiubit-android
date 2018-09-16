package com.v5ent.xiubit.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.v5ent.xiubit.MyApp;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import static com.igexin.sdk.GTServiceManager.context;

/**
 * @title:
 * @description: 分享工具类
 */
public class ShareUtils {

    public static final int SHARE_CONMMON = 1;  //调用系统常规分享
    public static final int SHARE_WXCIRCLE = 2;  //分享到微信朋友圈

    public static void shareToWxcircle(Activity activity,ArrayList<Uri> imageList,String contant){

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        intent.setAction("android.intent.action.SEND_MULTIPLE");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageList); //图片数据（支持本地图片的Uri形式）
        intent.putExtra("Kdescription", contant); //微信分享页面，图片上边的描述
        activity.startActivity(intent);
    }




    /**搜索本地图片并分享======================================================================================**/

    /**
     * 分享SD卡下jpg图片和文字描述到朋友圈
     */



    public static void shareLocalJpg(int ShareType) {
        List<String> paths = new ArrayList<String>();
        // 遍历 SD 卡下 .png 文件通过微信分享，保证SD卡根目录下有.png的文件
        File root = Environment.getExternalStorageDirectory();
        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jpg"))
                    return true;
                return false;
            }
        });

        for (File file : files) {
            paths.add(file.getAbsolutePath());
        }

        if (!isInstallWeChart(context)) {
            Toast.makeText(context, "您没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Uri> imageList = new ArrayList<Uri>();
        for (String picPath : paths) {
            File f = new File(picPath);
            if (f.exists()) {
                imageList.add(Uri.fromFile(f));
            }
        }
        if (imageList.size() == 0) {
            Toast.makeText(context, "图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (ShareType){
            case SHARE_CONMMON:
                shareImageLocal(imageList);
                break;
            case SHARE_WXCIRCLE:
                share9PicsToWXCircle(MyApp.getInstance().getApplicationContext(), "你好，成功的分享了多张照片到微信", paths);
                break;
        }
    }

    /**
     * 微信的SDK分享图片到好友
     *
     * @param context
     * @param path
     */
    public static void sharePicToFriendNoSDK(Context context, String path) {
        if (!isInstallWeChart(context)) {
            Toast.makeText(context, "您没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        // intent.setFlags(0x3000001);
        File f = new File(path);
        if (f.exists()) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(intent);
    }


    /**
     * 分享9图到朋友圈
     *
     * @param context
     * @param Kdescription 9图上边输入框中的文案
     * @param paths        本地图片的路径
     */
    public static void share9PicsToWXCircle(Context context, String Kdescription, List<String> paths) {
        if (!isInstallWeChart(context)) {
            Toast.makeText(context, "您没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        intent.setAction("android.intent.action.SEND_MULTIPLE");

        ArrayList<Uri> imageList = new ArrayList<Uri>();
        for (String picPath : paths) {
            File f = new File(picPath);
            if (f.exists()) {
                imageList.add(Uri.fromFile(f));
            }
        }
        if (imageList.size() == 0) {
            Toast.makeText(context, "图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageList); //图片数据（支持本地图片的Uri形式）
        intent.putExtra("Kdescription", Kdescription); //微信分享页面，图片上边的描述
        context.startActivity(intent);
    }

    /**
     * 微信sdk检查是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isInstallWeChart(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mm", 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void shareImageLocal(ArrayList<Uri> imageList) {
        
        Intent shareIt = new Intent("android.intent.action.SEND");
        shareIt.setType("image/*");
        shareIt.putExtra(Intent.EXTRA_SUBJECT, "Share");
        shareIt.putExtra(Intent.EXTRA_TEXT, "Content");
        //分享sd卡的图片
//        shareIt.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageList);
        shareIt.putExtra(Intent.EXTRA_STREAM, imageList.get(0));
        shareIt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIt, "Share"));
    }




}