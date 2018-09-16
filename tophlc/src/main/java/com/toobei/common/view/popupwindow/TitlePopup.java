package com.toobei.common.view.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.view.ActionItem;
import com.toobei.common.view.Util;

import java.util.ArrayList;

public class TitlePopup extends PopupWindow {
	private Context mContext;

	protected final int LIST_PADDING = 10;

	private static int margin;

	private Rect mRect = new Rect();

	private final int[] mLocation = new int[2];

	private int mScreenWidth, mScreenHeight;

	private boolean mIsDirty;

	private int popupGravity = Gravity.NO_GRAVITY;

	private OnItemOnClickListener mItemOnClickListener;

	private ListView mListView;

	private ArrayList<ActionItem> mActionItems = new ArrayList<ActionItem>();

	public TitlePopup(Context context) {
		this(context, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, margin);
	}

	public TitlePopup(Context context, int width, int height, int margin) {
		this.mContext = context;
		TitlePopup.margin = margin;
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);

		mScreenWidth = Util.getScreenWidth(mContext);
		mScreenHeight = Util.getScreenHeight(mContext);

		setWidth(width);
		setHeight(height);

		setBackgroundDrawable(new BitmapDrawable());

		setContentView(LayoutInflater.from(mContext).inflate(R.layout.title_popup, null));

		initUI();
	}

	private void initUI() {
		mListView = (ListView) getContentView().findViewById(R.id.title_list);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
				dismiss();

				if (mItemOnClickListener != null)
					mItemOnClickListener.onItemClick(mActionItems.get(index), index);
			}
		});
	}

	public void show(View view) {
		view.getLocationOnScreen(mLocation);

		mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(),
				mLocation[1] + view.getHeight());

		if (mIsDirty) {
			populateActions();
		}

		showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING - (getWidth() / 2),
				mRect.bottom + 40);
	}

	private void populateActions() {
		mIsDirty = false;
		mListView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = null;

				if (convertView == null) {
					textView = new TextView(mContext);
					textView.setTextColor(ContextCompat.getColor(mContext,android.R.color.white));
					textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
					// 标题居中改为靠左
					textView.setGravity(Gravity.LEFT);
					textView.setPadding(Util.dip2px(mContext,10), Util.dip2px(mContext,10), 0, Util.dip2px(mContext,10));
					textView.setSingleLine(true);
				} else {
					textView = (TextView) convertView;
				}

				ActionItem item = mActionItems.get(position);

				textView.setText(item.mTitle);

				textView.setCompoundDrawablePadding(margin);
				if(item.pointDrawbleID!=null){
					textView.setPadding(30, 32, 30, 30);
				}
				if (item.mDrawable != null) {
					textView.setCompoundDrawablesWithIntrinsicBounds(item.mDrawable, null,item.pointDrawbleID,
							null);

				}
				return textView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return mActionItems.get(position);
			}

			@Override
			public int getCount() {
				return mActionItems.size();
			}
		});
	}

	public void addAction(ActionItem action) {
		if (action != null) {
			mActionItems.add(action);
			mIsDirty = true;
		}
	}

	public void cleanAction() {
		if (mActionItems.isEmpty()) {
			mActionItems.clear();
			mIsDirty = true;
		}
	}

	public ActionItem getAction(int position) {
		if (position < 0 || position > mActionItems.size())
			return null;
		return mActionItems.get(position);
	}

	public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
		this.mItemOnClickListener = onItemOnClickListener;
	}

	public interface OnItemOnClickListener {
		void onItemClick(ActionItem item, int position);
	}
}