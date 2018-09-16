package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.toobei.common.TopApp;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.MsgDetail;
import com.toobei.common.entity.MsgListDatasDataEntity;
import com.toobei.common.entity.MsgUnreadCountEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.ListBlankLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.MineMsgNoticeListAdapter;
import com.toobei.tb.adapter.MineMsgPersonListAdapter;
import com.toobei.tb.util.C;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：消息中心
 * @date 2015-10-26
 */
public  class MineMsgCenter extends MyBaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

	private static final String INFOMATION_URL_TYPE_CLASSROOM = "4";
	private TextView msgTitleTV, noticeTitleTV, msgCountTV, noticeCountTV;
	private int newMsgCount1, newNoticeCount2;
	private ViewPager mPager;// 页卡内容
	private List<View> pagerViews; // Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView cursor;// 动画图片
	private boolean isNoticeInit = false;//公告 界面是否初始化
	private PullToRefreshListView noticeRefreshList, personRefreshList;
	private MineMsgNoticeListAdapter noticeListAdapter; //公告
	private MineMsgPersonListAdapter personListAdapter; //通知
	private int pageIndexNotice = 1, pageIndexPerson = 1;
	private boolean isReadedVisible = false;
	private boolean isDeleteVisible = false;  //删除通知
	private TextView rightBtn;
	private ViewGroup bottomDeleteMsgView;
	private CheckBox bottomCheckBox;
	private ListBlankLayout noticeListLayout, personListLayout;
	private View btnNoticeAllRead;  // 公告消息设为已读按钮
	public static final int TYPE_NOTICE = 1;  //公告
	public static final int TYPE_MSG = 2;  //消息
	private int mIntentType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int mIntentType = getIntent().getIntExtra("type",0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_msg);
		initView();
		getData(1, true);
		getHeadCountData();
	}



	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(MyApp.getInstance().getString(R.string.msg_center));
		headerLayout.showLeftBackButton();
		rightBtn = headerLayout.showRightTextButton(R.string.edit, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					//全局__消息_编辑
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(getString(R.string.umeng_global_msg_key), getString(R.string.umeng_global_msg_edit));
					MobclickAgent.onEvent(ctx, "global_module", map);
					if (currIndex == 1) {  //公告消息

					} else { //个人消息

						if (!isDeleteVisible && personListAdapter != null && personListAdapter.getCount() > 0) {
							bottomDeleteMsgView.setVisibility(View.VISIBLE);
							rightBtn.setText(R.string.complete);
							personListAdapter.setCheckVisible(true);
						} else {
							bottomDeleteMsgView.setVisibility(View.GONE);
							rightBtn.setText(R.string.edit);
							personListAdapter.setCheckVisible(false);
						}
						isDeleteVisible = !isDeleteVisible;

					}

			}
		});
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void initView() {
		initTextView();
		initViewPager();
		initImageView();
		initTopTitle();

		bottomDeleteMsgView = (ViewGroup) findViewById(R.id.
				mine_msg_person_delete_ll);
		btnNoticeAllRead = findViewById(R.id.text_nitice_bottom_delete);//公告消息全部设为已读
		bottomCheckBox = (CheckBox) findViewById(R.id.bottom_delete_checkbox);
		bottomCheckBox.setOnCheckedChangeListener(this);
		TextView text_bottom_delete = (TextView) findViewById(R.id.text_bottom_delete);
		text_bottom_delete.setOnClickListener(this);
		btnNoticeAllRead.setOnClickListener(this);

	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		pagerViews = new ArrayList<View>();
		pagerViews.add(getPersonListView()); //添加个人消息
		pagerViews.add(getNoticeListView()); //添加通知

		mPager.setAdapter(new MyPagerAdapter(pagerViews));
		if (mIntentType == TYPE_NOTICE){
			mPager.setCurrentItem(1);
		}else {
			mPager.setCurrentItem(0);
		}
		msgTitleTV.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
		mPager.addOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 公告视图
	 *
	 * @return
	 */
	private View getNoticeListView() {
		noticeListLayout = new ListBlankLayout(ctx, R.layout.layout_listview_in_viewpager);
		noticeRefreshList = (PullToRefreshListView) noticeListLayout.getContentView();
		noticeRefreshList.setId(R.id.msg_notice_refresh_view);
		ListView listView = noticeRefreshList.getRefreshableView();
		noticeListAdapter = new MineMsgNoticeListAdapter(ctx);
		listView.setOnItemClickListener(this);
		listView.setAdapter(noticeListAdapter);
		listView.setDividerHeight(0);
		listView.setDivider(null);
		listView.setSelector(R.color.Color_0);
		noticeRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
		noticeRefreshList.setOnRefreshListener(this);
		return noticeListLayout;
	}

	/**
	 * 通知视图
	 *
	 * @return
	 */
	private View getPersonListView() {
		if (personListLayout == null) {
			personListLayout = new ListBlankLayout(ctx, R.layout.layout_listview_in_viewpager);
			personRefreshList = (PullToRefreshListView) personListLayout.getContentView();
			personRefreshList.setId(R.id.msg_person_refresh_view);
			personRefreshList.setOnRefreshListener(this);
			ListView listView = personRefreshList.getRefreshableView();
			personListAdapter = new MineMsgPersonListAdapter(ctx);
			listView.setOnItemLongClickListener(this);
			listView.setAdapter(personListAdapter);
			listView.setDividerHeight(0);
			listView.setSelector(R.color.Color_0);
			//	listView.setVerticalScrollBarEnabled(false);
			personRefreshList.setMode(PullToRefreshBase.Mode.BOTH);

		}
		return personListLayout;
	}


	/**
	 * 初始化头标
	 */
	private void initTextView() {
		msgTitleTV = (TextView) findViewById(R.id.text1);
		noticeTitleTV = (TextView) findViewById(R.id.text2);
		msgTitleTV.setOnClickListener(new MyOnClickListener(0));
		findViewById(R.id.titleRL01).setOnClickListener(new MyOnClickListener(0));
		findViewById(R.id.titleRL02).setOnClickListener(new MyOnClickListener(2));
		noticeTitleTV.setOnClickListener(new MyOnClickListener(1));
		msgCountTV = (TextView) findViewById(R.id.text1_new_count);
		noticeCountTV = (TextView) findViewById(R.id.text2_new_count);
	}

	private void refreshHeadTextView() {
		if (newMsgCount1 > 0) { //通知
			int newCountTemp = newMsgCount1 < 99 ? newMsgCount1 : 99;
			msgCountTV.setText(newCountTemp + "");
			msgCountTV.setVisibility(View.VISIBLE);

		} else {
			msgCountTV.setVisibility(View.INVISIBLE);
		}
		if (newNoticeCount2 > 0) { //公告
			int newCount2Temp = newNoticeCount2 < 99 ? newNoticeCount2 : 99;
			noticeCountTV.setText(newCount2Temp + "");
			noticeCountTV.setVisibility(View.VISIBLE);
			btnNoticeAllRead.setVisibility(currIndex == 1 ? View.VISIBLE : View.GONE); // 删除通知
		} else {
			btnNoticeAllRead.setVisibility(View.GONE);
			noticeCountTV.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 初始化动画
	 */
	private void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = (TopApp.displayWidth) / 2 - PixelUtil.dip2px(ctx, 100);
		cursor.getLayoutParams().width = bmpW;
		offset = PixelUtil.dip2px(ctx, 100);// 偏移量
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	}

	/**
	 * ViewPager适配器
	 */
	class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}


		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position));
			return mListViews.get(position);
		}


		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}


	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {


			int one = bmpW + offset;// 页卡1 -> 页卡2 偏移量
			Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);//显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
			msgTitleTV.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
			msgTitleTV.getPaint().setFakeBoldText(false);
			noticeTitleTV.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
			noticeTitleTV.getPaint().setFakeBoldText(false);
			rightBtn.setText(R.string.edit);
			isDeleteVisible = false;
			isReadedVisible = false;
			personListAdapter.setCheckVisible(false);
			bottomDeleteMsgView.setVisibility(View.GONE);
			switch (arg0) {
				case 0:
//					HashMap<String, String> map0 = new HashMap<String, String>();
//					map0.put(getString(R.string.umeng_global_msg_key), getString(R.string.umeng_global_msg_notice));
//					MobclickAgent.onEvent(TopMineMsgCenter.this, "global_module", map0);

					btnNoticeAllRead.setVisibility(View.GONE); //通知页隐藏公告
					msgTitleTV.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
					msgTitleTV.getPaint().setFakeBoldText(true);
					if (isDeleteVisible) {
						rightBtn.performClick();
					}
					rightBtn.setVisibility(View.INVISIBLE);
					rightBtn.setVisibility(View.VISIBLE);
					break;
				case 1:

//					HashMap<String, String> map1 = new HashMap<String, String>();
//					map1.put(getString(R.string.umeng_global_msg_key), getString(R.string.umeng_global_msg_gonggao));
//					MobclickAgent.onEvent(TopMineMsgCenter.this, "global_module", map1);

					// 设置底部全部设为已读按钮  公告
					if (currIndex == 1 && newNoticeCount2 > 0) {
						noticeCountTV.setVisibility(View.VISIBLE);
						btnNoticeAllRead.setVisibility(View.VISIBLE);
					} else {
						btnNoticeAllRead.setVisibility(View.GONE);
					}

					noticeTitleTV.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
					noticeTitleTV.getPaint().setFakeBoldText(true);


					if (!isNoticeInit) {
						getData(2, true);
					}
					rightBtn.setVisibility(View.INVISIBLE);
					break;

			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 功能： 未读消息数
	 *
	 * @param //type       动态类别: 1、个人消息(通知) ，2、公告
	 * @param //openDialog
	 */
	private void getHeadCountData() {

		new MyNetAsyncTask(ctx, false) {
			private MsgUnreadCountEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService().msgPersonUnreadCount(TopApp.getInstance().getLoginService().token);
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						newNoticeCount2 = StringUtils.toInt(response.getData().getBulletinMsgCount(), 0);
						newMsgCount1 = StringUtils.toInt(response.getData().getPersonMsgCount(), 0);
						refreshHeadTextView();
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	/**
	 * 获取消息
	 *
	 * @param type       1 个人消息(通知) 2 公告
	 * @param openDialog
	 */
	private void getData(final int type, boolean openDialog) {

		new MyNetAsyncTask(ctx, openDialog) {
			private MsgListDatasDataEntity response;
			private List<MsgDetail> datas;

			@Override
			protected void doInBack() throws Exception {
				if (type == 1) {
					response = TopApp.getInstance().getHttpService().msgPersonPageList(TopApp.getInstance().getLoginService().token, pageIndexPerson + "");
				} else if (type == 2) {
					response = TopApp.getInstance().getHttpService().msgBulletinPageList(TopApp.getInstance().getLoginService().token, pageIndexNotice + "");
				}
			}

			@Override
			protected void onPost(Exception e) {

				if (type == 1) {
					personRefreshList.onRefreshComplete();
				} else if (type == 2) {
					noticeRefreshList.onRefreshComplete();
				}
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						datas = response.getData().getDatas();
						if (type == 2) {
							//	noticeRefreshList.setVisibility(View.VISIBLE);
							pageIndexNotice = response.getData().getPageIndex();
							if (datas == null || datas.size() == 0) {
								noticeListLayout.showBlank(R.string.blank_list_text_bulletin);
							} else {
								noticeListLayout.showContentView();
							}
							noticeRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
							if (pageIndexNotice == 1) {
								noticeListAdapter.refresh(datas);
							} else {
								noticeListAdapter.addAll(datas);
							}
							noticeListAdapter.notifyDataSetChanged();
							pageIndexNotice++;
							isNoticeInit = true;
							if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
								noticeRefreshList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
							}
						} else if (type == 1) {
							pageIndexPerson = response.getData().getPageIndex();
							//		personRefreshList.setVisibility(View.VISIBLE);
							if (datas == null || datas.size() == 0) {
								personListLayout.showBlank(R.string.blank_list_text_my_msg);
							} else {
								personListLayout.showContentView();
							}

							personRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
							if (pageIndexPerson == 1) {
								personListAdapter.refresh(datas);
							} else {
								personListAdapter.addAll(datas);
							}
							personListAdapter.notifyDataSetChanged();
							pageIndexPerson++;
							if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
								personRefreshList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
							}
						}
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	private void deleteData(final List<MsgDetail> list) {
		if (list == null || list.size() == 0) return;

		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					sb.append(list.get(i).getId());
					if (i < list.size() - 1) {
						sb.append(",");
					}
				}
				response = TopApp.getInstance().getHttpService().msgDeletes(TopApp.getInstance().getLoginService().token, sb.toString());

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						List<MsgDetail> datas = personListAdapter.getDatas();
						datas.removeAll(list);
						personListAdapter.refresh(datas);
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	//消息已读 公告消息-已读
	private void readData(final List<MsgDetail> list) {
		if (list == null || list.size() == 0) return;

		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					sb.append(list.get(i).getId());
					if (i < list.size() - 1) {
						sb.append(",");
					}
				}
				response = TopApp.getInstance().getHttpService().msgReaded(TopApp.getInstance().getLoginService().token, sb.toString());

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						getHeadCountData();
						//  btnNoticeAllRead.setVisibility(View.GONE);
					}
				}

			}
		}.execute();
	}

	/**
	 * 所有的公告设为已读
	 */
	private void allReadNoitice() {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {

				response = TopApp.getInstance().getHttpService().allReaded(TopApp.getInstance().getLoginService().token);

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						pageIndexNotice = 1;
						getData(2, false);
						btnNoticeAllRead.setVisibility(View.GONE);
					}
				}
			}
		}.execute();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		getHeadCountData();
		if (refreshView.getId() == R.id.msg_notice_refresh_view) { //公告
			pageIndexNotice = 1;

			getData(2, false);
		} else if (refreshView.getId() == R.id.msg_person_refresh_view) { //通知
			bottomCheckBox.setChecked(false);
			pageIndexPerson = 1;
			getData(1, false);
		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		getHeadCountData();
		if (refreshView.getId() == R.id.msg_notice_refresh_view) { //公告

			getData(2, false);
		} else if (refreshView.getId() == R.id.msg_person_refresh_view) {  //个人消息

			getData(1, false);
			newMsgCount1 = 0;
			msgCountTV.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.text_bottom_delete) {  // 删除个人通知
			newMsgCount1 = 0;
			refreshHeadTextView();
			deleteData(personListAdapter.getCheckedDatas());
		} else if (v.getId() == R.id.text_nitice_bottom_delete) {  // 公告全部设为已读
			//全局__消息_
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(getString(R.string.umeng_global_msg_key), getString(R.string.umeng_global_msg_all_read));
			MobclickAgent.onEvent(this, "global_module", map);
			allReadNoitice();
			newNoticeCount2 = 0;
			noticeCountTV.setVisibility(View.INVISIBLE); // 公告消息设为已读后隐藏消息数量
		}
	}

	@Override
	public void finish() {
		setResult(RESULT_OK);
		super.finish();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		personListAdapter.setAllItemChecked(isChecked);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		if (!isDeleteVisible) {
			rightBtn.performClick();
		}
		return true;
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(ctx, WebActivityCommon.class);
		// 2016/11/15 0015 INFOMATION_URL_TYPE_NOTICE informationDetailUrl	机构动态 、资讯 、课堂详情链接
		// String value = TopApp.getInstance().getDefaultSp().getBulletinDetailDefaultUrl() + "?msgId=" + noticeListAdapter.getItem(position - 1).getId();
//		String  value = MyApp.getInstance().getDefaultSp().getInformationDetailUrl() //
//				+"?type="+ INFOMATION_URL_TYPE_CLASSROOM+  //
//				"&id="+noticeListAdapter.getItem(position - 1).getId();
		String  value = C.URL_MSG_CENTER_MESSAGE_NOTICE +
				"?msgId="+noticeListAdapter.getItem(position - 1).getId();
		intent.putExtra("url", value);
		//   intent.putExtra("url", noticeListAdapter.getItem(position - 1).getLink());
		intent.putExtra("title", TopApp.getInstance().getString(R.string.msg_center));
		showActivity(ctx, intent);
		getHeadCountData();
		noticeListAdapter.getItem(position - 1).setRead("1");
		List<MsgDetail> msgDetails = new ArrayList<>();
		msgDetails.add(noticeListAdapter.getItem(position - 1));
		readData(msgDetails); //设置消息已读
		noticeListAdapter.notifyDataSetChanged();
	}
}
