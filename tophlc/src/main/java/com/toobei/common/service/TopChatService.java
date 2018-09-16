package com.toobei.common.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.toobei.common.TopApp;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.NetAsyncTaskNoCtx;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.TopPrefDao;
import com.toobei.common.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xsl781.utils.Log;
import org.xsl781.utils.SystemTool;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public abstract class TopChatService {
	private static final int REPLY_NOTIFY_ID = 0x123456;

	public EMMessage createTextMsg(String content, String toChatUsername,
			EMMessage.ChatType chatType) {
		if (content == null || content.length() == 0) {
			return null;
		}
		EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
		// 如果是群聊，设置chattype,默认是单聊
		if (chatType == ChatType.ChatRoom) {
			message.setChatType(ChatType.GroupChat);
		} else if (chatType == ChatType.GroupChat) {
			message.setChatType(ChatType.ChatRoom);
		}
		TextMessageBody txtBody = new TextMessageBody(content);
		// 设置消息body
		message.addBody(txtBody);
		// 设置要发给谁,用户username或者群聊groupid
		message.setReceipt(toChatUsername);
		return message;
	}

	public EMMessage createImgMsg(String imagePath, String toChatUsername,
			EMMessage.ChatType chatType) {
		EMMessage message = EMMessage.createSendMessage(EMMessage.Type.IMAGE);
		//如果是群聊，设置chattype,默认是单聊
		if (chatType != null)
			message.setChatType(chatType);

		ImageMessageBody body = new ImageMessageBody(new File(imagePath));
		// 默认超过100k的图片会压缩后发给对方，可以设置成发送原图
		body.setSendOriginalImage(true);
		message.addBody(body);
		message.setReceipt(toChatUsername);
		return message;
	}

	public void sendMsg(EMMessage message) {
		//发送消息
		EMChatManager.getInstance().sendMessage(message, new MyEMCallBack(message));
		if (TopApp.getInstance().IS_DEBUG)
			Log.getLog(getClass()).d("=======sendMsg");
	}

	/*	public EMMessage sendText(String content, String toChatUsername, EMMessage.ChatType chatType) {
			EMMessage message = createTextMsg(content, toChatUsername, chatType);

			return message;
		}*/

	/*	public EMMessage sendImgMsg(String imagePath, String toChatUsername, EMMessage.ChatType chatType) {
			EMMessage message = EMMessage.createSendMessage(EMMessage.Type.IMAGE);
			//如果是群聊，设置chattype,默认是单聊
			if (chatType != null)
				message.setChatType(chatType);

			ImageMessageBody body = new ImageMessageBody(new File(imagePath));
			// 默认超过100k的图片会压缩后发给对方，可以设置成发送原图
			// body.setSendOriginalImage(true);
			message.addBody(body);
			message.setReceipt(toChatUsername);
			//发送消息
			EMChatManager.getInstance().sendMessage(message, new MyEMCallBack(message));
			if (TopApp.getInstance().IS_DEBUG)
				Log.getLog(getClass()).d("=======sendImgMsg");
			return message;
		}*/

	public void sendTextAddConver(String content, String toChatUsername) throws Exception {
		if (toChatUsername.isEmpty()) {
			throw new Exception("用户通信id为空");
		}

		EMConversation conversation = EMChatManager.getInstance().getConversation(toChatUsername);
		EMMessage msg = createTextMsg(content, toChatUsername, null);
		sendMsg(msg);
		conversation.addMessage(msg);
	}

	/**
	 * 功能：设置用户的属性，
	 * 通过消息的扩展，传递客服系统用户的属性信息
	 * @param message
	 * @param userInfo 当前用户信息
	 * @param myCfp 我的理财师 金服版本必传
	 */
	public void setUserInfoAttribute(EMMessage message, UserInfo userInfo, UserInfo myCfp) {
		JSONObject weichatJson = getWeichatJSONObject(message);
		try {
			JSONObject visitorJson = new JSONObject();
			visitorJson.put("userNickname", userInfo.getNameOrPhone());
			visitorJson.put("trueName", userInfo.getUserName());
			visitorJson.put("phone", userInfo.getMobile());
			StringBuffer descSb = new StringBuffer();
			if (myCfp != null) {
				descSb.append("来源androidT呗");
				descSb.append(Utils.getAppVersion(TopApp.getInstance()));
				descSb.append(" 理财师姓名:");
				descSb.append(myCfp.getUserName());
				descSb.append(" 理财师电话:");
				descSb.append(myCfp.getMobile());
			} else {
				descSb.append("来源android貅比特");
				descSb.append(Utils.getAppVersion(TopApp.getInstance()));
			}
			visitorJson.put("description", descSb.toString());

			weichatJson.put("visitor", visitorJson);
			message.setAttribute("weichat", weichatJson);
			//我的理财师不为空表示金服端来的
			if (myCfp != null) {
				pointToSkillGroup(message, "toobei");
			} else {
				pointToSkillGroup(message, "lcds");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取消息中的扩展 weichat是否存在并返回jsonObject
	 * @param message
	 * @return
	 */
	private JSONObject getWeichatJSONObject(EMMessage message) {
		JSONObject weichatJson = null;
		try {
			String weichatString = message.getStringAttribute("weichat", null);
			if (weichatString == null) {
				weichatJson = new JSONObject();
			} else {
				weichatJson = new JSONObject(weichatString);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return weichatJson;
	}

	/**
	 * 指向某个具体客服，
	 * @param message 消息
	 * @param agentUsername 客服的登录账号
	 */
	private void pointToAgentUser(EMMessage message, String agentUsername) {
		try {
			JSONObject weichatJson = getWeichatJSONObject(message);
			weichatJson.put("agentUsername", agentUsername);
			message.setAttribute("weichat", weichatJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 技能组（客服分组）发消息发到某个组
	 * @param message 消息
	 * @param groupName 分组名称
	 */
	private void pointToSkillGroup(EMMessage message, String groupName) {
		try {
			JSONObject weichatJson = getWeichatJSONObject(message);
			weichatJson.put("queueName", groupName);
			message.setAttribute("weichat", weichatJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void resendMsg(EMMessage msg) {
		msg.status = EMMessage.Status.CREATE;
		EMChatManager.getInstance().sendMessage(msg, new MyEMCallBack(msg));
		if (TopApp.getInstance().IS_DEBUG)
			Log.getLog(getClass()).d("=======resendText");
	}

	public void onMsgReceiver(EMMessage msg, List<EMMessage> offLineMsgs) {
		if (TopApp.getInstance().IS_DEBUG)
			Log.getLog(getClass()).d("=======onMsgReceiver");
		afterReceiveMsg(msg, offLineMsgs);
	}

	public void onMsgSendFailure(EMMessage msg) {
		if (TopApp.getInstance().IS_DEBUG)
			Log.getLog(getClass()).d("=======onMsgSendFailure");
		MsgListenerService.getInstance().listenersOnMessageFailure(msg);
	}

	/*
		public void onMsgSendStart(EMMessage msg) {
			MsgListenerService.getInstance().listenersOnMsgSendStart(msg);
		}*/

	public void onMsgSendSuccess(EMMessage msg) {
		if (TopApp.getInstance().IS_DEBUG)
			Log.getLog(getClass()).d("=======onMsgSendSuccess");
		MsgListenerService.getInstance().listenersOnMsgSendSuccess(msg);
	}

	private void afterReceiveMsg(final EMMessage msg, final List<EMMessage> offLineMsgs) {
		if (msg == null && offLineMsgs == null) {
			return;
		}
		new NetAsyncTaskNoCtx() {
			UserInfo userInfo = null;
			EMMessage msg2 = msg;

			@Override
			protected void doInBack() throws Exception {
				if (msg == null && offLineMsgs != null && offLineMsgs.size() > 0) {
					msg2 = offLineMsgs.get(offLineMsgs.size() - 1);
				}

				switch (msg2.getType()) {
				case TXT:

					break;
				case IMAGE:

					break;
				default:
					break;
				}
				//缓存用户数据
				userInfo = TopApp.getInstance().getUserService()
						.getUserByEmIdAndSaveCache(msg2.getFrom());
			}

			@Override
			protected void onPost(Exception e) {

				if (e != null) {
					e.printStackTrace();
					ToastUtil.showCustomToast("网络不给力哦，请检查网络设置");
				} else {

					if (isNotifysNewMsg(msg)) {
						notifyMsg(TopApp.getInstance(), msg2, userInfo);
					}
				}
				MsgListenerService.getInstance().listenersOnMsgReceiver(msg2, offLineMsgs);
			}

		}.execute();
	}

	public int getAllUnReadMsgCount() {
		int unReadCount = 0;
		Hashtable<String, EMConversation> conversations = EMChatManager.getInstance()
				.getAllConversations();
		synchronized (conversations) {
			for (EMConversation conversation : conversations.values()) {
				if (conversation.getAllMessages().size() != 0) {
					unReadCount += conversation.getUnreadMsgCount();
				}
			}
		}
		return unReadCount;
	}

	protected abstract Intent getPendingIntent(Context context);

	public void notifyMsg(Context context, EMMessage msg, UserInfo userInfo) {
		if(userInfo == null){
			return;
		}
		int icon = context.getApplicationInfo().icon;
		String toChatUsername = userInfo.getEasemobAcct();
		Intent intent = getPendingIntent(context);
		intent.putExtra("toChatUsername", toChatUsername);
		PendingIntent pend = PendingIntent.getActivity(context, new Random().nextInt(), intent, 0);
		/*		PendingIntent pend = PendingIntent.getActivity(context, new Random().nextInt(), new Intent(
						context, TophlcApp.getInstance().getMainActivity()), 0);
		*/Notification.Builder builder = new Notification.Builder(context);
		CharSequence notifyContent = getNotifyContent(msg);
		CharSequence username = "";
		if (userInfo != null) {
			username = userInfo.getNameOrPhone();
		}
		builder.setContentIntent(pend).setSmallIcon(icon).setWhen(System.currentTimeMillis())
				.setTicker(username + "\n" + notifyContent).setContentTitle(username)
				.setContentText(notifyContent).setAutoCancel(true);
		NotificationManager man = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = builder.getNotification();
		TopPrefDao prefDao = TopApp.getInstance().getCurUserSp();
		if (prefDao.isVoiceNotify()) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		}
		if (prefDao.isVibrateNotify()) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		man.notify(REPLY_NOTIFY_ID, notification);
	}

	public void cancelNotification(Context ctx) {
		SystemTool.cancelNotification(ctx, REPLY_NOTIFY_ID);
	}

	public CharSequence getNotifyContent(EMMessage msg) {
		switch (msg.getType()) {
		case TXT:
			/*if (EmotionService.haveEmotion(getRemark())) {
				return WZZApp.getInstance().getString(R.string.emotion);
			} else {
				return getRemark();
			}*/
			TextMessageBody body = (TextMessageBody) msg.getBody();
			return body.getMessage();
		default:
			return "新消息";
		}
	}

	/**
	 * 判断是否需要通知提醒
	 */
	private boolean isNotifysNewMsg(EMMessage msg) {
		TopPrefDao prefDao = TopApp.getInstance().getCurUserSp();
		if (!prefDao.isNotifyWhenNews())
			return false;

		return MsgListenerService.getInstance().getMsgListenerCount() <= 0;
	}
}
