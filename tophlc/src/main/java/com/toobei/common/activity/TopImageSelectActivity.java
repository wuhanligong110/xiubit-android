package com.toobei.common.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopTitleBaseActivity;
import com.toobei.common.data.ImageSelectAdapter;
import com.toobei.common.data.ImageSelectAdapter.OnImageClick;
import com.toobei.common.entity.ImageFloder;
import com.toobei.common.entity.ImageResponseEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.event.CardScanEvent;
import com.toobei.common.event.UpCardScanDataEvent;
import com.toobei.common.event.UpLoadHeadImageEvent;
import com.toobei.common.utils.BitmapUtil;
import com.toobei.common.utils.C;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.popupwindow.ListImageDirPopupWindow;
import com.toobei.common.view.popupwindow.ListImageDirPopupWindow.OnImageDirSelected;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.ui.ViewInject;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public abstract class TopImageSelectActivity extends TopTitleBaseActivity implements OnImageDirSelected,
        OnItemClickListener{
    private ProgressDialog mProgressDialog;
    //	public static ImageSelectActivity activity;
    private String tag = "";

    private boolean isMult = false;//是否多选
    public static int isSelectedCount = 0;//已经选择图片数量
    //	private TextView rightTextView;

    public final static int IMG_SELECT_MAX_COUNT = 5;//最多可以选几张图

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片文件夹,默认为Camera
     */
    private File mImgDir;

    //	private File maxImgDir;//图片最多 的文件夹
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    private GridView mGirdView;
    private ImageSelectAdapter mAdapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

    private RelativeLayout mBottomLy;

    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private boolean isGetPicFromCamera = true;
    private int scanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //	activity = this;
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        tag = getIntent().getExtras().getString(C.TAG);
        scanType = getIntent().getIntExtra("ScanType",0);
        if (tag.equals(C.UPLOAD_DECLARATION_PIC) || tag.equals(C.FOR_CARDSCAN)) {
            isGetPicFromCamera = false;
        }
        initView();
        getImages();
        initEvent();
        initTopTitle();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.image_select_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGirdView = null;
        mAdapter = null;
        mImgs = null;
        mHandler = null;
        mBottomLy = null;

        mChooseDir = null;
        mImageCount = null;
        ImageLoader.getInstance().clearMemoryCache();
        System.gc();
    }

    /*
         * 友盟埋点统计
         */
    public abstract void EventCount();

    private int mScreenHeight;

    private ListImageDirPopupWindow mListImageDirPopupWindow;

    private String curNewTopicPath;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    private void initTopTitle() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showLeftBackButton();
        if (isMult) {
            headerLayout.showRightTextButton(R.string.finished, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 跳转到发表界面
                    //	String str = BitmapUtil.scaleImage(path, PathUtils.getChatFileDir() + "clip_comp"
                    //			+ System.currentTimeMillis(), maxSide, maxSize);
                }
            });
            headerLayout.showTitle(getResources().getString(R.string.text_image_select_titile)
                    + isSelectedCount + "/" + IMG_SELECT_MAX_COUNT);
        } else {
            headerLayout.showTitle(R.string.text_image_select_titile);
        }
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            com.toobei.common.utils.ToastUtil.showCustomToast("暂无外部存储");
            return;
        }
        // 显示进度条
        mProgressDialog = ViewInject.getProgress(this,
                TopApp.getInstance().getString(R.string.hardLoading), false);
        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = TopImageSelectActivity.this.getContentResolver();

                // 只查询jpeg和jpg,png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + " =? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{
                                "image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED
                                + " DESC");
                if (mCursor != null) {

                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));

                        Log.d("TAG", path);
                        // 拿到第一张图片的路径
                        if (firstImage == null)
                            firstImage = path;
                        // 获取该图片的父路径名
                        File parentFile = new File(path).getParentFile();
                        if (parentFile == null)
                            continue;
                        String dirPath = parentFile.getAbsolutePath();
                        ImageFloder imageFloder = null;
                        // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                        if (mDirPaths.contains(dirPath)) {
                            continue;
                        } else {
                            mDirPaths.add(dirPath);
                            // 初始化imageFloder
                            imageFloder = new ImageFloder();
                            imageFloder.setDir(dirPath);
                            imageFloder.setFirstImagePath(path);
                        }
                        if (parentFile.list() == null)
                            continue;

                        List<String> strs = getAbsolutePathFromFolder(parentFile);
                        if (mImgs == null) {
                            mImgs = strs;
                        } else {
                            mImgs.addAll(strs);
                        }

                        int picSize = strs == null ? 0 : strs.size();
                        totalCount += picSize;

                        imageFloder.setCount(picSize);
                        mImageFloders.add(imageFloder);
                        //默认相册
                        /*	if (parentFile.getName().contains("Camera")
									|| parentFile.getName().contains("DCIM")) {
								mImgDir = parentFile;
							}*/

                        //默认显示图片最多的文件 夹
						/*if (picSize > mPicsSize) {
							mPicsSize = picSize;
							mImgDir = parentFile;
						}*/
                    }
                    mCursor.close();

                }
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();
    }

    private void getAdapter() {
        if (mImgDir == null) {
            if (mImgs == null || mImgs.size() == 0) {
                com.toobei.common.utils.ToastUtil.showCustomToast("一张图片没扫描到");
                //	Utils.showToast(this, "一张图片没扫描到", Toast.LENGTH_SHORT, false);
            }
            mAdapter = new ImageSelectAdapter(this, mImgs, null, isMult, isGetPicFromCamera);
        } else {
            //mImgs = array2ListDESC(mImgDir.list());
            mChooseDir.setText(mImgDir.getName());
            mImgs = getNamesFromFolder(mImgDir);
            mAdapter = new ImageSelectAdapter(this, mImgs, mImgDir.getAbsolutePath(), isMult, isGetPicFromCamera);
        }
        if (isMult) {
            mAdapter.setOnImageClick(new OnImageClick() {

                @Override
                public void OnImageClicked(boolean isSelect, String path) {
                    if (isSelect) {
                        isSelectedCount++;
                    } else {
                        isSelectedCount--;
                    }
                    headerLayout.showTitle(getResources().getString(
                            R.string.text_image_select_titile)
                            + isSelectedCount + "/" + IMG_SELECT_MAX_COUNT);
                }
            });
        }
    }

    private List<File> getSortChildImgFiles(File parentFile) {
        File[] imgfile = parentFile.listFiles(new FileFilter() {

            @Override
            public boolean accept(File f) {
                String tmp = f.getName().toLowerCase();
                return tmp.endsWith(".png") || tmp.endsWith(".jpg") || tmp.endsWith(".jpeg");
            }
        });
        ArrayList<File> mList = new ArrayList<File>();
        int len = imgfile.length;
        for (int i = 0; i < len; i++) {
            mList.add(imgfile[i]);
        }
        //	System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(mList, new FileComparator());
        return mList;
    }

    /**
     * 功能：获取文件 夹内所有文件 名，并按修改时间倒序排列
     *
     * @param parentFile
     * @return
     */
    private List<String> getNamesFromFolder(File parentFile) {
        return listFileToName(getSortChildImgFiles(parentFile));
    }

    private List<String> getAbsolutePathFromFolder(File parentFile) {
        return listFileToAbsolutePath(getSortChildImgFiles(parentFile));
    }

    private class FileComparator implements Comparator<File> {

        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() < rhs.lastModified()) {
                return 1;//最后修改的照片在前
            } else if (lhs.lastModified() == rhs.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);
        mGirdView.setOnItemClickListener(this);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
    }

    /**
     * 为View绑定数据
     */
    private void data2View() {
        getAdapter();
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }

    private List<String> array2ListDESC(String[] strs) {
        ArrayList<String> list = new ArrayList<String>();
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                list.add(strs[strs.length - 1 - i]);
            }
        }
        return list;
    }

    private List<String> listFileToName(List<File> files) {
        ArrayList<String> list = new ArrayList<String>();
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }

    private List<String> listFileToAbsolutePath(List<File> files) {
        ArrayList<String> list = new ArrayList<String>();
        for (File file : files) {
            list.add(file.getAbsolutePath());
        }
        return list;
    }

    private List<String> array2ListDESCAddPath(String parentPath, String[] strs) {
        ArrayList<String> list = new ArrayList<String>();
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                list.add(parentPath + "/" + strs[strs.length - 1 - i]);
            }
        }
        return list;
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(LayoutParams.MATCH_PARENT,
                (int) (mScreenHeight * 0.7), mImageFloders, LayoutInflater.from(
                getApplicationContext()).inflate(R.layout.image_select_list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private boolean isPicture(String filename) {
        String filename2 = filename.toLowerCase();
        return filename2.endsWith(".jpg") || filename2.endsWith(".png") || filename2.endsWith(".jpeg");
    }

    @Override
    public void selected(ImageFloder floder) {

        mImgDir = new File(floder.getDir());
        mImgs = getNamesFromFolder(mImgDir);
		/*mImgs = array2ListDESC(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return isPicture(filename);
			}
		}));*/
		/*
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));*/
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        // mAdapter.notifyDataSetChanged();
        //mAdapter = new ImageSelectAdapter(this, mImgs, mImgDir.getAbsolutePath(), isMult);
        getAdapter();
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == C.REQUEST_CAMARA) { //从相机返回


                if (tag.equals(C.TAG_USER_FACE_CHANGED)) {  //头像修改
                    skipCropActivity(ctx,PathUtils.getAvatarTmpPath());
//                    PhotoUtil.startImageCrop(this, PathUtils.getAvatarTmpPath(), 200, 200,
//                            C.REQUEST_CROP_SYSTEM);

                } else if (tag.equals(C.TAG_CHAT_PIC_SELECT)) {  //客服聊天
                    Intent intent = new Intent();
                    intent.putExtra("strPath", PathUtils.getTmpPath());
                    // 返回intent
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (tag.equals(C.UPLOAD_DECLARATION_PIC)) {
                    Intent intent = new Intent();
                    intent.putExtra("strPath", PathUtils.getTmpPath());
                    // 返回intent
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    protected abstract void skipCropActivity(Context ctx, String avatarTmpPath);

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if (tag.equals(C.TAG_USER_FACE_CHANGED)) {
            if (position == 0) {
                goToCamara(PathUtils.getAvatarTmpPath());
            } else {
                skipCropActivity(ctx,mAdapter.getItemPath(position));
//                PhotoUtil.startImageCrop(this, mAdapter.getItemPath(position), 200, 200,
//                        C.REQUEST_CROP_SYSTEM);
            }
        } else if (tag.equals(C.TAG_CHAT_PIC_SELECT)) {
            if (position == 0) {
                goToCamara(PathUtils.getTmpPath());
            } else {
                Intent intent = new Intent();
                intent.putExtra("strPath", mAdapter.getItemPath(position));
                // 返回intent
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (tag.equals(C.UPLOAD_DECLARATION_PIC)) {

            if (position == 0) {
                goToCamara(PathUtils.getTmpPath());
            } else {
                Intent intent = new Intent();
                intent.putExtra("strPath", mAdapter.getItemPath(position));
                // 返回intent
                setResult(RESULT_OK, intent);
                finish();
            }

        }else if (tag.equals(C.FOR_CARDSCAN)){
            EventBus.getDefault().post(new UpCardScanDataEvent(new File(mAdapter.getItemPath(position)),scanType));
            finish();
        }
    }


    private void goToCamara(String path) {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(path);
        if (file.exists())
            file.delete();
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
        startActivityForResult(intentFromCapture, C.REQUEST_CAMARA);
    }


    private void clipPics() {
        new MyNetAsyncTask(this, false) {

            @Override
            protected void doInBack() throws Exception {
                List<String> mSelectedImage = mAdapter.getmSelectedImage();

            }

            @Override
            protected void onPost(Exception e) {
                if (e != null) {
                } else {

                }
            }
        }.execute();
    }


    //重设头像
    public void reSetHeadImage(final String imgPath) {

        uploadHeadImage(imgPath, new UpdateViewCallBack<ImageResponseEntity>() {

            private String faceUrlMd5;

            @Override
            public void updateView(Exception e, ImageResponseEntity response) {
                faceUrlMd5 = response.getInfo().getMd5();
                new MyNetAsyncTask(ctx, false) {

                    LoginResponseEntity loginResponseEntity;

                    @Override
                    protected void doInBack() throws Exception {
                        loginResponseEntity = TopApp.getInstance().getHttpService().personcenterUploadIcon(TopApp.getInstance().getLoginService().token, faceUrlMd5);
                    }

                    @Override
                    protected void onPost(Exception e) {
                        if (e == null && loginResponseEntity != null) {
                            if (loginResponseEntity.getCode().equals("0")) {
                                TopApp.getInstance().getLoginService().getCurUser().setHeadImage(faceUrlMd5);
                                TopApp.getInstance().getUserService().saveAndCache(TopApp.getInstance().getLoginService().getCurUser());
                                //	TopApp.getInstance().getUserService().displayUserFace(TopApp.getInstance().getLoginService().getCurUser().getHeadImage(), mineImgUserFace, false);
                               // ToastUtil.showCustomToast("上传成功");
                                EventBus.getDefault().post(new UpLoadHeadImageEvent(faceUrlMd5));

                                Intent intent = new Intent();
                                intent.putExtra("faceUrlMd5", faceUrlMd5);
                                // 返回intent
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                ToastUtil.showCustomToast(loginResponseEntity.getErrorsMsgStr());
                            }
                        } else {
                            ToastUtil.showCustomToast(getString(com.toobei.common.R.string.pleaseCheckNetwork));
                        }
                    }
                }.execute();

            }
        });
    }

    /**
     * 上传头像到服务器 返回一个md5
     *
     * @param callBack 更新头像接口
     */
    private void uploadHeadImage(final String imgPath, final UpdateViewCallBack<ImageResponseEntity> callBack) {
        new MyNetAsyncTask(ctx, false) {
            ImageResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().personcenterUploadImageFile(new File(imgPath));
            }

            @Override
            protected void onPost(Exception e) {
                if (response != null && response.getInfo() != null && response.getInfo().getMd5() != null) {
                    callBack.updateView(null, response);
                }
            }
        }.execute();
    }


}
