package com.toobei.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/** 
 * 图片下载 
 *  
 */  
public class DownLoadImageService implements Runnable {  
    private String url;  
    private Context context;  
    private ImageDownLoadCallBack callBack;  
    private File currentFile;  
    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {  
        this.url = url;  
        this.callBack = callBack;  
        this.context = context;  
    }  
  
    @Override  
    public void run() {  
        File file = null;  
        Bitmap bitmap = null;  
        try {  
//            file = Glide.with(context)  
//                    .load(url)  
//                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)  
//                    .get();  
            bitmap = Glide.with(context)  
                    .load(url)  
                    .asBitmap()  
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)  
                    .get();  
            if (bitmap != null){  
                // 在这里执行图片保存方法  
                saveImageToGallery(context,bitmap);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
  
    public void saveImageToGallery(Context context, Bitmap bmp) {  
        // 首先保存图片
       File appDir = new File(PathUtils.getImagePath());
        if (!appDir.exists()) {  
            appDir.mkdirs();  
        }  
        String fileName = System.currentTimeMillis() + ".jpg";  
        currentFile = new File(appDir, fileName);  
  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(currentFile);  
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);  
            fos.flush();
            callBack.onDownLoadSuccess(currentFile,bmp);
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (fos != null) {  
                    fos.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        // 其次把文件插入到系统图库  
//        try {  
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),  
//                    currentFile.getAbsolutePath(), fileName, null);  
//        } catch (FileNotFoundException e) {  
//            e.printStackTrace();  
//        }  
  
        // 最后通知图库更新  
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,  
                Uri.fromFile(new File(currentFile.getPath()))));  
    }  
}  

interface ImageDownLoadCallBack {

    void onDownLoadSuccess(File file ,Bitmap bitmap);
  
    void onDownLoadFailed();  
}  