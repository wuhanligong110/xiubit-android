package com.toobei.common.data;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.toobei.common.R;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.LinkedList;
import java.util.List;

public class ImageSelectAdapter extends BaseListAdapter<String> {

    public boolean isMult = false;//是否多选
    public boolean isGetPicFromCamera = true;//是否多选

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public List<String> mSelectedImage = new LinkedList<String>();

    /**
     * 文件夹路径
     */
    private String mDirPath;

    private OnImageClick onImageClick;

    public interface OnImageClick {
        void OnImageClicked(boolean isSelect, String path);
    }

    public ImageSelectAdapter(Context ctx, List<String> datas, String dirPath, boolean isMult) {
        super(ctx, datas);
        this.mDirPath = dirPath;
        this.isMult = isMult;
    }

    public ImageSelectAdapter(Context ctx, List<String> datas, String dirPath, boolean isMult, boolean isGetPicFromCamera) {
        super(ctx, datas);
        this.mDirPath = dirPath;
        this.isMult = isMult;
           this.isGetPicFromCamera = isGetPicFromCamera;
    }

    @Override
    public long getItemId(int position) {
        if (position == 0) return 0;
        else return position--;
    }

    @Override
    public int getCount() {
        if (datas == null || datas.size() == 0) {
            return 1;
        }
        return isGetPicFromCamera ? datas.size() + 1 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position > 0 ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_select_grid_item, null);
        }
        final ImageView image = ViewHolder.findViewById(convertView, R.id.id_item_image);
        if (position == 0 && isGetPicFromCamera) {
            image.setImageResource(R.drawable.image_select_camera);
            return convertView;
        }

        final String strUrl = getItem(isGetPicFromCamera ? position - 1 : position);
        if (mDirPath != null) {

            Glide.with(ctx).load("file:///" + mDirPath + "/" + strUrl).dontAnimate().into(image);
            //  ImageLoader.getInstance().displayImage("file:///" + mDirPath + "/" + strUrl, image, PhotoUtil.normalImageOptions);
        } else {
            Glide.with(ctx).load("file:///" + strUrl).dontAnimate().into(image);
            //  ImageLoader.getInstance().displayImage("file:///" + strUrl, image ,PhotoUtil.normalImageOptions);
        }
        image.setColorFilter(null);
        //设置ImageView的点击事件
        if (isMult) {
            final ImageView mSelect = ViewHolder.findViewById(convertView, R.id.id_item_select);
            mSelect.setVisibility(View.VISIBLE);
            image.setOnClickListener(new OnClickListener() {
                //选择，则将图片变暗，反之则反之
                @Override
                public void onClick(View v) {

                    // 已经选择过该图片
                    if (mSelectedImage.contains(mDirPath + "/" + strUrl)) {
                        mSelectedImage.remove(mDirPath + "/" + strUrl);
                        image.setColorFilter(null);
                        mSelect.setImageResource(R.drawable.img_pic_no_select);
                        //	ImageSelectActivity.isSelectedCount--;
                        onImageClick.OnImageClicked(false, mDirPath + "/" + strUrl);
                    }/* else if (ImageSelectActivity.isSelectedCount < ImageSelectActivity.IMG_SELECT_MAX_COUNT) {
                        mSelectedImage.add(mDirPath + "/" + strUrl);
						image.setColorFilter(Color.parseColor("#77000000"));
						mSelect.setImageResource(R.drawable.img_pic_selected);
						//						ImageSelectActivity.isSelectedCount++;
						onImageClick.OnImageClicked(true, mDirPath + "/" + strUrl);
					}*/

                }
            });
            /**
             * 已经选择过的图片，显示出选择过的效果
             */
            if (mSelectedImage.contains(mDirPath + "/" + strUrl)) {
                image.setColorFilter(Color.parseColor("#77000000"));
                mSelect.setImageResource(R.drawable.img_pic_selected);
            }
        }
        return convertView;
    }

    public String getItemPath(int position) {
        if (position == 0 && isGetPicFromCamera) return null;
        if (mDirPath != null) {
            return mDirPath + "/" + getItem(isGetPicFromCamera ? position - 1 : position);
        } else {
            return getItem(isGetPicFromCamera ? position - 1 : position);
        }
    }

    public List<String> getmSelectedImage() {
        return mSelectedImage;
    }

    public void setmSelectedImage(List<String> mSelectedImage) {
        this.mSelectedImage = mSelectedImage;
    }

    public String getmDirPath() {
        return mDirPath;
    }

    public void setmDirPath(String mDirPath) {
        this.mDirPath = mDirPath;
    }

    public boolean isMult() {
        return isMult;
    }

    public void setMult(boolean isMult) {
        this.isMult = isMult;
    }

    public void setOnImageClick(OnImageClick onImageClick) {
        this.onImageClick = onImageClick;
    }

}
