package com.toobei.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopTitleBaseActivity;
import com.toobei.common.data.ChatMsgAdapter;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.service.MsgListener;
import com.toobei.common.service.MsgListenerService;
import com.toobei.common.utils.C;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;

import org.xsl781.ui.xlist.XListView;
import org.xsl781.utils.SimpleNetTask;
import org.xsl781.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：对话界面
 * @date 2015-12-16
 */
public abstract class TopChatActivity extends TopTitleBaseActivity implements OnClickListener,
		XListView.IXListViewListener, OnTouchListener, MsgListener {
	public static final int PAGE_SIZE = 20;
	public static final int CHATTYPE_SINGLE = 1;
	public static final int CHATTYPE_GROUP = 2;
	public static final int CHATTYPE_CHATROOM = 3;

	private static final int IMAGE_REQUEST = 0;

	private ChatMsgAdapter adapter;
	private List<EMMessage> msgs = new ArrayList<EMMessage>();

	View chatTextLayout, sendBtn;
	private Button btnSendImg;
	protected EditText contentEdit;
	private XListView xListView;

	private boolean isStoped = false;
	private EMConversation conversation;
	private int chatType;
	private int pageSize = PAGE_SIZE;
	private UserInfo chatUserInfo;
	protected boolean isCallCenter = false;
	private String askforredpackage;

	public void onCreate(Bundle savedInstanceState) {
		//	isStatuBarTran = false;
		super.onCreate(savedInstanceState);
		TopApp.getInstance().getLoginService().initLoginEM();
		ctx = this;
		//	setContentView(R.layout.chat_layout);
		findView();
		initByIntent(getIntent());
		if (!isCallCenter) {
			getChatUserInfo();
		}
		adapter.setToChatUserInfo(chatUserInfo);
	}

	@Override
	public void refreshAfterLogin() {
		super.refreshAfterLogin();
		initByIntent(getIntent());
		if (!isCallCenter) {
			getChatUserInfo();
		}
		adapter.setToChatUserInfo(chatUserInfo);
	}

	@Override
	public void onResume() {
		super.onResume();
		isStoped = false;
		EMChatManager.getInstance().getChatOptions().setNumberOfMessagesLoaded(PAGE_SIZE + 20);
		MsgListenerService.getInstance().registerMsgListener(getClass().getName(), this);
	}

	@Override
	protected void onPause() {
		isStoped = true;
		MsgListenerService.getInstance().unregisterMsgListener(getClass().getName());
		conversation.markAllMessagesAsRead();
		super.onPause();
	}

	protected abstract Intent getImageSelectActivityIntent();

	protected abstract Intent getPopuImgViewActivityIntent();

	private void initByIntent(Intent intent) {
		initData(intent);
		initListView();
		loadNewMsg(false);
		setSoftInputMode();
		//ChatService.getInstance().cancelNotification(ctx);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		initByIntent(intent);
	}

	private void initListView() {
		adapter = new ChatMsgAdapter(ctx, msgs, getPopuImgViewActivityIntent(), getChatBgResId());
		adapter.setDatas(msgs);
		xListView.setCacheColorHint(ContextCompat.getColor(ctx,R.color.Color_0));
		xListView.setSelector(R.drawable.xlistview_item);
		xListView.setAdapter(adapter);
		xListView.setPullRefreshEnable(true);
		xListView.setPullLoadEnable(false);
		xListView.setXListViewListener(this);
		xListView.setOnTouchListener(this);
	}

	private void initTopTitle() {
		if (headerLayout == null)
			return;
		setTopTitle();
		headerLayout.showLeftBackButton(this);
	}

	private void setTopTitle() {
		contentEdit.setHint(isCallCenter ? "       客服时间: 工作日9:00-18:00" : "");
		if (isCallCenter) {
			headerLayout.showTitle("在线咨询");
		} else {
			//设置输入提示
			headerLayout.showTitle("与" + chatUserInfo.getNameOrPhone() + "的互动");
		}
	}

	private void findView() {
		xListView = (XListView) findViewById(R.id.listview);

		contentEdit = (EditText) findViewById(R.id.textEdit);

		chatTextLayout = findViewById(R.id.chatTextLayout);
		chatTextLayout = findViewById(R.id.chatTextLayout);
		sendBtn = findViewById(R.id.sendBtn);
		sendBtn.setOnClickListener(this);
		btnSendImg = (Button) findViewById(R.id.btn_send_img);

		btnSendImg.setBackgroundResource(getBtnSendImgResId());
		sendBtn.setBackgroundResource(getSendBtnResId());

		btnSendImg.setOnClickListener(this);
		contentEdit.setOnClickListener(this);
		askforredpackage = getIntent().getStringExtra("askforredpackage");
		if (askforredpackage != null && !askforredpackage.equals("")) {
			contentEdit.setText(askforredpackage);
			//sendText();
		}
	}

	protected void initData(Intent intent) {
		chatType = getIntent().getIntExtra("chatType", CHATTYPE_SINGLE);

		String toChatUsername = getIntent().getStringExtra("toChatUsername");
		isCallCenter = getIntent().getBooleanExtra("isCallCenter", false);
		if (TopApp.getInstance().getUserService().isCallServiceUser(toChatUsername)) {
			isCallCenter = true;
		}
		if (isCallCenter) {
			chatUserInfo = new UserInfo();
			chatUserInfo.setEasemobAcct(toChatUsername);
			chatUserInfo.setUserName("客服");
		} else {
			chatUserInfo = TopApp.getInstance().getUserService()
					.getUserByEmIdFromLocal(toChatUsername);
		}

		if (chatUserInfo == null) {
			com.toobei.common.utils.ToastUtil.showCustomToast("当前账号数据异常,请重新进入");
			finish();
			return;
		}

		conversation = EMChatManager.getInstance().getConversation(toChatUsername);
		if (TopApp.getInstance().getLoginService().getCurUser() == null
				|| TopApp.getInstance().getLoginService().getCurUser().getEasemobAcct() != null
				&& TopApp.getInstance().getLoginService().getCurUser().getEasemobAcct()
						.equals(toChatUsername)) {

			com.toobei.common.utils.ToastUtil.showCustomToast("当前账号异常");
			finish();
		}
		initTopTitle();
	}

	public void addMsgAndScrollToLast(EMMessage msg) {
		adapter.add(msg);
		hideBottomLayoutAndScrollToLast();
	}

	public View getMsgViewByMsg(EMMessage msg) {
		int msgPos = adapter.getItemPosById(msg.getMsgId());
		if (msgPos < 0) {
			return null;
		}
		// Logger.d("msgPos=" + msgPos);
		int firstPos = xListView.getFirstVisiblePosition() - xListView.getHeaderViewsCount();
		int wantedChild = msgPos - firstPos;
		// Logger.d("wanted child pos=" + wantedChild);
		if (wantedChild < 0 || wantedChild >= xListView.getChildCount()) {
			// Logger.d("Unable to get view for desired position");
			return null;
		}
		return xListView.getChildAt(wantedChild);
	}

	public void loadNewMsg(boolean openDialog) {
		new GetDataTask(ctx, openDialog, true, null).execute();
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//pageSize += PAGE_SIZE;
				//	EMChatManager.getInstance().getChatOptions().setNumberOfMessagesLoaded(pageSize);
				new GetDataTask(ctx, false, false, adapter.getDatas().get(0).getMsgId()).execute();
				//	new GetDataTask(ctx, false, false, null).execute();
			}
		}, 100);
	}

	@Override
	public void onLoadMore() {
	}

	class GetDataTask extends MyNetAsyncTask {
		boolean res;
		List<EMMessage> msgs;
		boolean scrollToLast = true;
		String oldestMsgId;

		GetDataTask(Context cxt, boolean openDialog, boolean scrollToLast, String oldestMsgId) {
			super(cxt, openDialog);
			this.scrollToLast = scrollToLast;
			this.oldestMsgId = oldestMsgId;
		}

		@Override
		protected void doInBack() throws Exception {
			if (oldestMsgId == null) {
				msgs = conversation.getAllMessages();
			} else {
				msgs = conversation.loadMoreMsgFromDB(oldestMsgId, PAGE_SIZE);
			}
			/*for (int i = 0; i < msgs.size(); i++) {
				conversation.getMessage(i);
			}*/
		}

		@Override
		protected void onPost(Exception e) {
			if (e == null) {
				if (isStoped) {
					return;
				}
				int lastN = adapter.getCount();
				if (oldestMsgId != null) {
					adapter.getDatas().addAll(0, msgs);
				} else {
					adapter.setDatas(msgs);
				}
				adapter.notifyDataSetChanged();
				int newN = adapter.getCount();

				if (scrollToLast) {
					scrollToLast();
				} else {
					xListView.setSelection(newN - lastN - 2);
				}

				if (msgs.size() <= 0) {
					xListView.setPullRefreshEnable(false);
				} else {
					xListView.setPullRefreshEnable(true);
				}
			} else {
				xListView.setPullRefreshEnable(false);
			}
			if (xListView.getPullRefreshing()) {
				xListView.stopRefresh();
			}
		}
	}

	private void getChatUserInfo() {

		new MyNetAsyncTask(ctx, false) {
			UserInfo response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getUserService()
						.getUserFromMyServerAndSaveCache(chatUserInfo.getEasemobAcct());
				if (response != null) {
					chatUserInfo = response;
				}
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					setTopTitle();

				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.sendBtn) {
			sendText();
		} else if (v.getId() == R.id.backBtn) {
			finish();
		} else if (v.getId() == R.id.btn_send_img) {
			//			Intent intent = new Intent(this, ImageSelectActivity.class);
			Intent intent = getImageSelectActivityIntent();
			intent.putExtra(C.TAG, C.TAG_CHAT_PIC_SELECT);
			startActivityForResult(intent, IMAGE_REQUEST);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case IMAGE_REQUEST:
				String strPath = data.getExtras().getString("strPath");
				if (strPath != null) {
					sendImageByPath(strPath);
				} else {
					ToastUtil.showCustomToast("图片选择出错！");
				}
				/*String localSelectPath = parsePathByReturnData(data);
				if (localSelectPath != null) {
					sendImageByPath(localSelectPath);
				} else {
					Utils.showToast(ctx, "图片选择出错！", Toast.LENGTH_SHORT, false);
				}*/
				break;
			}
		}
		//		hideBottomLayout();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void hideBottomLayoutAndScrollToLast() {
		scrollToLast();
	}

	private void sendText() {

		final String contString = contentEdit.getText().toString();
		if (TextUtils.isEmpty(contString) == false) {
			new SendMsgTask(ctx, false) {
				@Override
				EMMessage createMsg() throws Exception {
					return TopApp.getInstance().getChatService()
							.createTextMsg(contString, chatUserInfo.getEasemobAcct(), null);
				}
			}.execute();
			contentEdit.setText("");
		}
	}

	private void sendImageByPath(final String localSelectPath) {
		new SendMsgTask(ctx, false) {
			@Override
			EMMessage createMsg() throws Exception {
				return TopApp.getInstance().getChatService()
						.createImgMsg(localSelectPath, chatUserInfo.getEasemobAcct(), null);
			}
		}.execute();
	}

	public abstract class SendMsgTask extends SimpleNetTask {
		EMMessage msg;

		protected SendMsgTask(Context cxt, boolean bool) {
			super(cxt, bool);
		}

		@Override
		protected void doInBack() throws Exception {
			msg = createMsg();
			//如果是跟客服聊天，则发送相关参数 
			if (isCallCenter) {
				TopApp.getInstance()
						.getChatService()
						.setUserInfoAttribute(msg,
								TopApp.getInstance().getLoginService().getCurUser(),
								TopApp.getInstance().getLoginService().getMyCfpUserInfo());
			}
			TopApp.getInstance().getChatService().sendMsg(msg);
		}

		@Override
		public void onSucceed() {
			conversation.addMessage(msg);
			//	addMsgAndScrollToLast(msg);
		}

		abstract EMMessage createMsg() throws Exception;
	}

	public void scrollToLast() {
		// xListView.smoothScrollToPosition(xListView.getCount() - 1);
		xListView.setSelection(adapter.getCount() - 1);
	}

	/*
		public static void goUserChat(Activity ctx, String userId) {
			Intent intent = getUserChatIntent(ctx, userId);
			ctx.startActivity(intent);
		}

		public static Intent getUserChatIntent(Context ctx, String userId) {
			Intent intent = new Intent(ctx, TopChatActivity.class);
			//	intent.putExtra(CHAT_USER_ID, userId);
			//	intent.putExtra(SINGLE_CHAT, true);
			return intent;
		}
	*/
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Utils.hideSoftInputView(TopChatActivity.this);
			return true;
		}
		return false;

	}

	public String getToChatUsername() {
		return chatUserInfo.getEasemobAcct();
	}

	@Override
	public String getListenerId() {
		return getClass().getName();
	}

	@Override
	public void onMsgReceiver(EMMessage message, List<EMMessage> offLineMsgs) {
		String username = null;
		//群组消息
		if (message.getChatType() == ChatType.GroupChat
				|| message.getChatType() == ChatType.ChatRoom) {
			username = message.getTo();
		} else {
			//单聊消息
			username = message.getFrom();
		}
		//如果是当前会话的消息，刷新聊天页面
		if (username.equals(getToChatUsername())) {
			loadNewMsg(false);
			//声音和震动提示有新消息
			//	HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(message);
		} else {
			//如果消息不是和当前聊天ID的消息
			//	HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
		}

	}

	@Override
	public void onMsgSendFailure(EMMessage msg) {
		loadNewMsg(false);
	}

	@Override
	public void onMsgSendSuccess(EMMessage msg) {
		loadNewMsg(false);
	}

	@Override
	protected int getContentLayout() {
		return R.layout.chat_layout;
	}

	protected abstract int getBtnSendImgResId();

	protected abstract int getSendBtnResId();

	protected abstract int getChatBgResId();
}
