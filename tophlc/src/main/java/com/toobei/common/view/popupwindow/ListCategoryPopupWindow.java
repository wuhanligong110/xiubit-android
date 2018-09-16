package com.toobei.common.view.popupwindow;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.toobei.common.R;
import com.toobei.common.data.CategorySelectListAdapter;
import com.toobei.common.entity.AccountType;

import java.util.List;

public class ListCategoryPopupWindow extends BasePopupWindowForListView<AccountType> {
	private ListView mListDir;
	private AccountType selected;
	private CategorySelectListAdapter adapter;

	public ListCategoryPopupWindow(int width, int height, List<AccountType> datas,
			AccountType selected, View convertView) {
		super(convertView, width, height, true, datas);
		this.selected = selected;
		adapter = new CategorySelectListAdapter(context, mDatas, selected);
		mListDir.setAdapter(adapter);
	}

	@Override
	public void initViews() {
		mListDir = (ListView) findViewById(R.id.id_list_dir);
		mListDir.setVerticalScrollBarEnabled(true);
	}

	public interface OnItemSelected {
		void selected(AccountType category);
	}

	private OnItemSelected onItemSelected;

	public void setOnItemSelected(OnItemSelected onItemSelected) {
		this.onItemSelected = onItemSelected;
	}

	@Override
	public void initEvents() {
		mListDir.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (onItemSelected != null) {
					onItemSelected.selected(mDatas.get(position));
				}
			}
		});
	}

	@Override
	public void init() {

	}

	@Override
	protected void beforeInitWeNeedSomeParams(Object... params) {
	}

	public void setSelected(AccountType selected) {
		this.selected = selected;
		if (adapter != null) {
			adapter.setSelected(selected);
			adapter.notifyDataSetChanged();
		}
	}

	public AccountType getSelected() {
		return selected;
	}

}
