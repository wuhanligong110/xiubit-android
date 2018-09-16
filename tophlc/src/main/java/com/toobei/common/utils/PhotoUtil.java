package com.toobei.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.data.GlideRoundTransform;

import org.xsl781.utils.Logger;

import java.io.File;

public class PhotoUtil {
    /**
     * 显示理财师还是t呗头像
     *
     * @param headImage       imgurl
     * @param mineImgUserFace imageView
     */
    public static void displayUserFace(String headImage, ImageView mineImgUserFace) {
        if (mineImgUserFace == null) {
            return;
        }
        boolean isCustomer = true;  // * @param isCustomer true是客户，falce为理财师
        if ("channel".equals(TopApp.getInstance().getHttpService().getAppKind())) {
            isCustomer = false;
        }
        TopApp.getInstance().getUserService().displayUserFace(TopApp.getInstance().getHttpService().getImageServerBaseUrl() + headImage, mineImgUserFace, isCustomer);

    }


    /**
     * 使用imageLoader加载图片
     *
     * @param imgUrl imgurl
     * @param iv     imageView
     */
    public static void loadImageByImageLoader(String imgUrl, ImageView iv) {
        if (iv == null || TextUtils.isEmpty(imgUrl)) {
            if (iv == null) {
                Logger.e("控件为null");
            } else {
                Logger.e("imgUrl==为空");

            }
            return;
        }
        if (!TextUtils.isEmpty(imgUrl) && !imgUrl.startsWith("http")) {
            imgUrl = TopApp.getInstance().getHttpService().getImageServerBaseUrl() + imgUrl; //默认md5添加图片服务器地址
        }
        Logger.d("PhotoUtil imageLoader加载图片===>>> imgUrl==" + imgUrl);
        ImageLoader.getInstance().displayImage(imgUrl, iv, PhotoUtil.normalImageOptions);

    }

    public static void getBitmapFromUrl (Context ctx,String imgUrl , final ObjectCallBack<Bitmap> callback){
      Glide.with(ctx). 
                load(imgUrl).
                asBitmap().into(new SimpleTarget<Bitmap>() {
          @Override
          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
              callback.backObject(resource);
          }
      });

    }
    

    /**
     * 使用Glide 加载图片
     *
     * @param context context
     * @param imgUrl  imgurl
     * @param iv      imageView
     */
    public static void loadImageByGlide(Context context, String imgUrl, ImageView iv) {
        
//        if (!TextUtils.isEmpty(imgUrl) && !imgUrl.startsWith("http")) {
//            imgUrl = TopApp.getInstance().getHttpService().getImageServerBaseUrl() + imgUrl; //默认md5添加图片服务器地址
//        }
        imgUrl = TopApp.getInstance().getHttpService().getImageUrlFormMd5(imgUrl);
        Logger.d("PhotoUtil使用Glide加载图片===>>> imgUrl==" + imgUrl);
        Glide.with(context). //
                load(imgUrl). //
                dontAnimate().
//                placeholder(com.toobei.common.R.drawable.empty_photo). // 设置空图片
//                error(com.toobei.common.R.drawable.image_load_fail). //加载失败的图片
//                diskCacheStrategy(DiskCacheStrategy.ALL). //缓存全尺寸
                signature(new StringSignature(imgUrl)).  //签名验证
                into(iv);
    }


    /**
     * 使用Glide 加载图片
     *
     * @param context context
     * @param imgUrl  imgurl
     * @param iv      imageView
     */
    public static void loadImageByGlide(Context context, String imgUrl, ImageView iv,int emptyImageid) {

//        if (!TextUtils.isEmpty(imgUrl) && !imgUrl.startsWith("http")) {
//            imgUrl = TopApp.getInstance().getHttpService().getImageServerBaseUrl() + imgUrl; //默认md5添加图片服务器地址
//        }
        imgUrl = TopApp.getInstance().getHttpService().getImageUrlFormMd5(imgUrl);
        Logger.d("PhotoUtil使用Glide加载图片===>>> imgUrl==" + imgUrl);
        Glide.with(context). //
                load(imgUrl). //
                placeholder(emptyImageid). // 设置空图片
//                error(emptyImageid). //错误图片
//                error(com.toobei.common.R.drawable.image_load_fail). //加载失败的图片
        diskCacheStrategy(DiskCacheStrategy.ALL). //缓存全尺寸
                signature(new StringSignature(imgUrl)).  //签名验证
                into(iv);
    }
//
//    public static void loadImageOnly(Context context, String imgUrl) {
//
////        if (!TextUtils.isEmpty(imgUrl) && !imgUrl.startsWith("http")) {
////            imgUrl = TopApp.getInstance().getHttpService().getImageServerBaseUrl() + imgUrl; //默认md5添加图片服务器地址
////        }
//        imgUrl = TopApp.getInstance().getHttpService().getImageUrlFormMd5(imgUrl);
//        Logger.d("PhotoUtil使用Glide缓存图片===>>> imgUrl==" + imgUrl);
//        Glide.with(context). //
//              load(imgUrl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//    }

//    public static void loadImageByPicasso (Context context, String imgUrl, ImageView iv,int emptyImageid){
//        imgUrl = TopApp.getInstance().getHttpService().getImageUrlFormMd5(imgUrl);
//        Picasso.with(context).load(imgUrl).placeholder(emptyImageid).into(iv);
//    }

    /**
     * 使用Glide 加载图片带圆角
     *
     * @param context context
     * @param imgUrl  imgurl
     * @param iv      imageView
     */
    public static void loadImageWithRoundCorner(Context context, String imgUrl, ImageView iv, int corner) {
        if (iv == null) {
            Logger.e("控件为null");
        } else {
            Logger.e("imgUrl==为空");

        }
        if (!TextUtils.isEmpty(imgUrl) && !imgUrl.startsWith("http")) {
            imgUrl = TopApp.getInstance().getHttpService().getImageServerBaseUrl() + imgUrl; //默认md5添加图片服务器地址
        }
        Logger.d("PhotoUtil使用Glide 加载图片带圆角===>>> imgUrl==" + imgUrl);
        Glide.with(context). //
                load(imgUrl). //
                placeholder(com.toobei.common.R.drawable.empty_photo). // 设置空图片
                error(com.toobei.common.R.drawable.image_load_fail). //加载失败的图片
                diskCacheStrategy(DiskCacheStrategy.ALL). //缓存全尺寸
                transform(new GlideRoundTransform(context, corner)). //图片圆角
                into(iv);
    }

    /**
     * 将图片变为圆角
     *
     * @param bitmap 原Bitmap图片
     * @param pixels 图片圆角的弧度(单位:像素(px))
     * @return 带有圆角的图片(Bitmap 类型)
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 将图片转化为圆形头像
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;

            left = 0;
            top = 0;
            right = width;
            bottom = width;

            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;

            float clip = (width - height) / 2;

            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        // canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

	/*

		public static Bitmap compressImage(float width, float height, Bitmap image) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出	
				baos.reset();//重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			//开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			//现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
			float hh = height;//这里设置高度为800f
			float ww = width;//这里设置宽度为480f
			//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;//be=1表示不缩放
			if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;//设置缩放比例
			//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			isBm = new ByteArrayInputStream(baos.toByteArray());
			bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
			return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
		}*/

    public static DisplayImageOptions feedbackImageOptions = new DisplayImageOptions.Builder().considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ALPHA_8).resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
            .build();

    public static ImageLoaderConfiguration getImageLoaderConfig(Context context, File cacheDir) {
        return new ImageLoaderConfiguration.Builder(context).threadPoolSize(5).memoryCacheExtraOptions(1080, 1920).threadPriority(Thread.NORM_PRIORITY)
                /*	.memoryCache(new WeakMemoryCache())
                    .denyCacheImageMultipleSizesInMemory()*/
//                .memoryCache(new UsingFreqLimitedMemoryCache(10 * 1024 * 1024))
                .memoryCache(new WeakMemoryCache())  //使用弱引用，避免OOM
                .memoryCacheSize(10 * 1024 * 1024).discCacheSize(150 * 1024 * 1024)
                // 将保存的时候的URI名称用MD5 加密
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(200) //缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 7 * 1000, 30 * 1000))
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // Remove for release app
                .build();
    }

    public static DisplayImageOptions normalImageOptions = new DisplayImageOptions.Builder().//
            showImageForEmptyUri(R.drawable.empty_photo).showImageOnFail(R.drawable.image_load_fail).//
            showImageOnLoading(R.drawable.empty_photo).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Config.ALPHA_8).resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位

            //.displayer(new RoundedBitmapDisplayer(20))
            //.displayer(new FadeInBitmapDisplayer(300))// 淡入
            .build();
    public static DisplayImageOptions normalImageOptions2 = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.empty_photo).showImageOnFail(R.drawable.image_load_fail).showImageOnLoading(R.drawable.empty_photo).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Config.ALPHA_8).resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            //.displayer(new RoundedBitmapDisplayer(20))
            //.displayer(new FadeInBitmapDisplayer(300))// 淡入
            .build();
    public static DisplayImageOptions orgTitleImageOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.empty_photo).showImageOnFail(null).showImageOnLoading(null).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Config.ARGB_8888).resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            //.displayer(new RoundedBitmapDisplayer(20))
            //.displayer(new FadeInBitmapDisplayer(300))// 淡入
            .build();

    public static DisplayImageOptions chatImageOptions = new DisplayImageOptions.Builder()
    /*.showImageForEmptyUri(R.drawable.empty_photo)
    .showImageOnLoading(R.drawable.empty_photo).showImageOnFail(R.drawable.image_load_fail)*/.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Config.ALPHA_8).resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
            //.displayer(new RoundedBitmapDisplayer(20))
            //.displayer(new FadeInBitmapDisplayer(300))// 淡入
            .build();

    /**
     * 功能：获取图片的宽高
     *
     * @param imageFile = ImageLoader.getInstance().getDiscCache().get(url)
     * @return 角标0为宽度 角标1为高度
     */
    public static int[] getXYByFile(File imageFile) {
        int[] arry = new int[2];
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        if (imageFile != null) {
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        }
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        arry[0] = w;
        arry[1] = h;
        return arry;
    }

    /**
     * 功能：调用系统剪切图片功能
     *
     * @param path        为需要剪切的图片的路径
     * @param //outPath   //    剪切后保存的路径
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    public static void startImageCrop(Activity activity, String path, int outputX, int outputY, int requestCode) {
        Intent intent = null;
        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        /*if(outPath != null){
            File outFile = new File(outPath);
			if (outFile.exists()) {
				outFile.delete();
			}
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
		}*/
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // face detection
        activity.startActivityForResult(intent, requestCode);
    }

    @SuppressWarnings("deprecation")
    public static DisplayImageOptions getDisplayImageOptionsFromLoadUrl(String url) {
        File file = ImageLoader.getInstance().getDiscCache().get(url);
        DisplayImageOptions options = null;
        if (file != null) {
            Drawable drawable = new BitmapDrawable(BitmapFactory.decodeFile(file.getAbsolutePath()));
            options = new DisplayImageOptions.Builder().showImageOnLoading(drawable).showImageForEmptyUri(drawable).showImageOnFail(drawable).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ALPHA_8).resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
                    .build();
        } else {
            options = normalImageOptions;
        }
        //.displayer(new RoundedBitmapDisplayer(20))
        //.displayer(new FadeInBitmapDisplayer(300))// 淡入
        return options;
    }

    /**
     * 返回
     *
     * @param
     * @param ctx
     */
    public static void displayImageByUri(TopBaseActivity ctx, ImageView imageView, String localPath, String url, String originUrl, ImageLoadingListener loadinglistener) {
        String uri = url;
        DisplayImageOptions ImageOptions = PhotoUtil.normalImageOptions;
        if (!StringUtil.isEmpty(originUrl)) {
            ImageOptions = PhotoUtil.getDisplayImageOptionsFromLoadUrl(url);
            ImageLoader.getInstance().displayImage(originUrl, imageView, ImageOptions, loadinglistener);
        } else if (StringUtil.isEmpty(localPath)) {
            uri = url;
            ImageLoader.getInstance().displayImage(url, imageView, ImageOptions);
        } else {
            File file = new File(localPath);
            if (file.exists()) {
                Glide.with(ctx). //
                        load(file). //
                        placeholder(com.toobei.common.R.drawable.empty_photo). // 设置空图片
//                        error(com.toobei.common.R.drawable.image_load_fail). //加载失败的图片
//                        diskCacheStrategy(DiskCacheStrategy.ALL). //缓存全尺寸
                        into(imageView);
//                uri = "file://" + localPath;
//                ImageLoader.getInstance().displayImage("file://" + localPath, imageView, ImageOptions);
            } else {
                uri = url;
                ImageLoader.getInstance().displayImage(url, imageView, ImageOptions);
            }
        }
        //		String cacheUri = imageLoader.getLoadingUriForView(imageView);
        //	String cacheUri = imageLoader.getDiscCache().get(imageLoader.getLoadingUriForView(imageView)).getPath();
        //		System.out.println("-------- > 缓存uri " + uri);
        //		System.out.println("-------- > 缓存路径 " + PathUtils.getCacheDir() + uri.substring(uri.lastIndexOf("/") + 1));
        //		System.out.println("-------- > 读取到的缓存路径 " + imageLoader.getDiscCache().get(uri).getPath());
        //		System.out.println("-------- > 显示的路径 " + PathUtils.getCacheDir()
        //				+ cacheUri.substring(cacheUri.lastIndexOf("/") + 1));
        //		System.out.println("------> 内存中对象 " + imageLoader.getMemoryCache().get(uri));
        //		System.out.println("------> 内存中对象 第二种uri " + imageLoader.getMemoryCache().get(cacheUri));
//        System.out.println("------>image disc cache " + ImageLoader.getInstance().getDiscCache().get(uri));//此方法有效
        //		System.out.println("------> 硬盘缓存第二种 是否存在" + imageLoader.getDiscCache().get(cacheUri).exists());
        //显示的路径 /storage/emulated/0/yisharing/wzz/cache/-1222894868
        // 读取到的缓存路径 /storage/emulated/0/storage/emulated/0/yisharing/wzz/cache/1496764804

    }

    /**
     * 功能：专为聊天中 聊天消息中的图片显示
     *
     * @param imageView
     * @param localPath
     * @param url
     * @return
     */
    public static File displayChatImageByUri(ImageView imageView, String localPath, String url) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions ImageOptions = PhotoUtil.chatImageOptions;
        String uri = url;
        if (localPath == null) {
            uri = url;
            imageLoader.displayImage(url, imageView, ImageOptions);
        } else {
            File file = new File(localPath);
            if (file.exists()) {
                uri = "file://" + localPath;
                imageLoader.displayImage("file://" + localPath, imageView, ImageOptions);
            } else {
                uri = url;
                imageLoader.displayImage(url, imageView, ImageOptions);
            }
        }
        return imageLoader.getDiscCache().get(uri);
    }

    /**
     * 将彩色图转换为纯黑白二色
     *
     * @param //位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                //分离三原色
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                //转化成灰度像素
                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        //新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        //设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

        return newBmp;
    }

    /*
     * 获取屏幕截图
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("TTTTTTTTActivity", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return viewToBitmap(v, v.getWidth(), v.getHeight());
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public static Bitmap viewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    /**
     * 截取模板预览图
     */
    public Bitmap saveBitmapCache(View view) {
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(getViewBitmap(view));
        return bitmap;
    }

}
