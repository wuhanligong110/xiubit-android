package com.toobei.common.utils;

import android.os.Environment;

import com.toobei.common.TopApp;

import org.xsl781.utils.MD5Util;
import org.xsl781.utils.Utils;

import java.io.File;

public class PathUtils {

    private static String appPath = null;

    public synchronized static String getAppPath() {
        if (appPath != null)
            return appPath;
        appPath = TopApp.getInstance().getDefaultSp().getAppPath();
        if (appPath == null) {
            File file = Environment.getExternalStorageDirectory();
            appPath = checkAppPathIsOk(file);
            if (appPath == null) {
                File parent = file.getParentFile();
                File[] list = parent.listFiles();
                for (File file2 : list) {
                    appPath = checkAppPathIsOk(file2);
                    if (appPath != null) {
                        break;
                    }
                }
            }
            if (appPath != null)
                TopApp.getInstance().getDefaultSp().setAppPath(appPath);
        }
        //  Log.d("PathUtils", "========== PathUtils getAppPath() = " + appPath);
        return appPath;
    }

    public synchronized static String resetAppPath() {

        File file = Environment.getExternalStorageDirectory();
        appPath = checkAppPathIsOk(file);
        if (appPath == null) {
            File parent = file.getParentFile();
            File[] list = parent.listFiles();
            for (File file2 : list) {
                appPath = checkAppPathIsOk(file2);
                if (appPath != null) {
                    break;
                }
            }
        }
        if (appPath != null)
            TopApp.getInstance().getDefaultSp().setAppPath(appPath);

        return appPath;
    }

    private static String checkAppPathIsOk(File file) {
        String str = checkAndMkdirs(file.getPath() + TopApp.getInstance().appPathRela);
        if (new File(str).exists()) {
            return str;
        }
        return null;
    }

    public static String checkAndMkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        } /*else {
            if (file != null && file.list() != null && file.list().length > 500) {
                file.delete();
            }
            file.mkdirs();
        }*/
        return dir;
    }

    public static String getCacheDir() {
        String dir = getAppPath() + "cache/";
        return checkAndMkdirs(dir);
    }

    /**
     * 功能：获取缓存路径,金服和理财师在不同路径下
     *
     * @param phone 当前用户的号码，如果为null表示用默认路径
     * @return
     */
    public static String getCacheDataDir(String phone) {
        String root = TopApp.getInstance().getFilesDir().getAbsolutePath();
        StringBuffer dir = new StringBuffer(root);
        if (StringUtil.isEmpty(phone)) {
            dir.append("/cache/default/");
        } else {
            dir.append("/cache/");
            dir.append(MD5Util.MD5(phone));
            dir.append("/");
        }
        return checkAndMkdirs(dir.toString());
    }

    public static String getSaveImage() {
        String dir = getAppPath() + "img/";
        return checkAndMkdirs(dir);
    }

    public static String getAvatarDir() {
        String dir = getAppPath() + "avatar/";
        return checkAndMkdirs(dir);
    }

    public static String getAvatarTmpPath() {
        return getAvatarDir() + "tmp";
    }

    public static String getFeedbackTmpPath() {
        return getChatFileDir() + "_Feedback_tmp";
    }

    public static String getChatFileDir() {
        String dir = getAppPath() + "files/";
        checkAndMkdirs(dir);
        return dir;
    }

    public static File getCacheFileDir() {
        String dir = getAppPath() + "cache/";
        checkAndMkdirs(dir);
        return new File(dir);
    }

    public static String getExceptionPath() {
        String dir = getAppPath() + "exception/";
        return checkAndMkdirs(dir);
    }

    public static String getChatFile(String id) {
        String dir = getChatFileDir();
        String path = dir + id;
        return path;
    }

    public static String getRecordTmpPath() {
        return getChatFileDir() + "_tmp";
    }

    public static String getUUIDFilePath() {
        return getChatFile(Utils.uuid());
    }

    public static String getTmpPath() {
        return getAppPath() + "tmp";
    }

    public static String getImagePath() {
        String dir = getAppPath() + "image/";
        return checkAndMkdirs(dir);
    }
}
