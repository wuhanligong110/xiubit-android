package com.toobei.common.data;

import java.util.List;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.R;
import com.toobei.common.entity.ImageFloder;
import com.toobei.common.utils.PhotoUtil;

public class ImageSelectDirListAdapter extends BaseListAdapter<ImageFloder> {

	public ImageSelectDirListAdapter(Context ctx) {
		super(ctx);
	}

	public ImageSelectDirListAdapter(Context ctx, List<ImageFloder> datas) {
		super(ctx, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.image_select_list_dir_item, null);
		}
		ImageFloder item = getItem(position);
		ImageView image = ViewHolder.findViewById(convertView, R.id.id_dir_item_image);
		TextView textName = ViewHolder.findViewById(convertView, R.id.id_dir_item_name);
		TextView textCount = ViewHolder.findViewById(convertView, R.id.id_dir_item_count);
		ImageLoader.getInstance().displayImage("file:///" + item.getFirstImagePath(),
				image, PhotoUtil.normalImageOptions);
		textName.setText(item.getName());
		textCount.setText(item.getCount() + "张");
		return convertView;
	}
}
