package com.toobei.common.data;

import android.content.Intent;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.easeui.EaseSmileUtils;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.service.TopUserService;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.TimeUtils;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;
import org.xsl781.ui.view.RoundImageView;
import org.xsl781.utils.PixelUtil;

import java.util.List;

public class ChatMsgAdapter extends BaseListAdapter<EMMessage> implements OnLongClickListener {
    int msgViewTypes = 16;
    private Intent popuImgViewActivityIntent;
    private UserInfo toChatUserInfo;
    private int chatBgResId;

    //	private WZZActivity activity;

    public interface MsgViewType {
        int COME_TEXT = 0;
        int TO_TEXT = 1;
        int COME_IMAGE = 2;
        int TO_IMAGE = 3;
        int COME_AUDIO = 4;
        int TO_AUDIO = 5;
    }

    public ChatMsgAdapter(TopBaseActivity ctx, List<EMMessage> datas, Intent popuImgViewActivityIntent, int chatBgResId) {
        super(ctx, datas);
        this.chatBgResId = chatBgResId;
        this.popuImgViewActivityIntent = popuImgViewActivityIntent;
    }

    public int getItemPosById(String objectId) {
        for (int i = 0; i < getCount(); i++) {
            EMMessage itemMsg = datas.get(i);
            if (itemMsg.getMsgId().equals(objectId)) {
                return i;
            }
        }
        return -1;
    }

    public EMMessage getItem(String objectId) {
        for (EMMessage msg : datas) {
            if (msg.getMsgId().equals(objectId)) {
                return msg;
            }
        }
        return null;
    }

    public int getItemViewType(int position) {
        EMMessage entity = datas.get(position);
        boolean comeMsg = isMsgCome(entity);
        switch (entity.getType()) {
            case TXT:
                return comeMsg ? MsgViewType.COME_TEXT : MsgViewType.TO_TEXT;
            case IMAGE:
                return comeMsg ? MsgViewType.COME_IMAGE : MsgViewType.TO_IMAGE;
            case VOICE:
                return comeMsg ? MsgViewType.COME_AUDIO : MsgViewType.TO_AUDIO;
            default:
                return comeMsg ? MsgViewType.COME_TEXT : MsgViewType.TO_TEXT;
        }
    }

    private boolean isMsgCome(EMMessage msg) {
        return msg.direct == EMMessage.Direct.RECEIVE ? true : false;
    }

    public int getViewTypeCount() {
        return msgViewTypes;
    }

    public View getView(int position, View conView, ViewGroup parent) {
        //	Logger.d("-------> position = " + position + "数据总数：" + datas.size());
        if (position >= datas.size()) return conView;
        final EMMessage msg = datas.get(position);

        int itemViewType = getItemViewType(position);
        boolean isComMsg = isMsgCome(msg);
        if (conView == null) {
            conView = createViewByType(itemViewType);
        }
        TextView sendTimeView = ViewHolder.findViewById(conView, R.id.sendTimeView);
        TextView textContent = ViewHolder.findViewById(conView, R.id.textContent);
        final RoundImageView avatarView = ViewHolder.findViewById(conView, R.id.avatar);
        ImageView imageView = ViewHolder.findViewById(conView, R.id.imageView);
        View statusSendFailed = ViewHolder.findViewById(conView, R.id.status_send_failed);
        View statusSendSucceed = ViewHolder.findViewById(conView, R.id.status_send_succeed);
        View statusSendStart = ViewHolder.findViewById(conView, R.id.status_send_start);
        if (toChatUserInfo != null) {
            TopUserService userService = TopApp.getInstance().getUserService();
            if (userService != null) {
                if (isComMsg) {
                    if (userService.isCallServiceUser(toChatUserInfo.getEasemobAcct())) {
                        //显示客服头像
                        avatarView.setImageResource(R.drawable.img_im_call_service_face);
                    } else {
                        userService.displayUserFace(toChatUserInfo.getHeadImage(), avatarView, !toChatUserInfo.isCfp());
                    }
                } else {
                    userService.displayUserFace(userService.getCurUser().getHeadImage(), avatarView, TopApp.getInstance().appName.equals("tb"));
                }


            }

        }

        if (sendTimeView != null) {
            if (position == 0 || TimeUtils.haveTimeGap(datas.get(position - 1).getMsgTime(), msg.getMsgTime())) {
                sendTimeView.setVisibility(View.VISIBLE);
                sendTimeView.setText(TimeUtils.millisecs2DateStringNew(msg.getMsgTime()));
            } else {
                sendTimeView.setVisibility(View.GONE);
            }
        }

        switch (msg.getType()) {
            case TXT:
                TextMessageBody txtBody = (TextMessageBody) msg.getBody();
                if (textContent == null) break;


                Spannable span = EaseSmileUtils.getSmiledText(ctx, txtBody.getMessage());
                // 设置内容
                textContent.setText(span, TextView.BufferType.SPANNABLE);
                //textContent.setText(txtBody.getMessage());
                //左边
                if (isComMsg) {
                    textContent.setBackgroundResource(chatBgResId);
                    textContent.setPadding(PixelUtil.dip2px(ctx, 14), PixelUtil.dip2px(ctx, 8), PixelUtil.dip2px(ctx, 10), PixelUtil.dip2px(ctx, 8));
                } else {
                    textContent.setBackgroundResource(R.drawable.chat_right_qp);
                    textContent.setPadding(PixelUtil.dip2px(ctx, 10), PixelUtil.dip2px(ctx, 8), PixelUtil.dip2px(ctx, 14), PixelUtil.dip2px(ctx, 8));
                }

                break;
            case IMAGE:
                ImageMessageBody imgBody = (ImageMessageBody) msg.getBody();
                PhotoUtil.displayChatImageByUri(imageView, imgBody.getLocalUrl(), imgBody.getRemoteUrl());
                setImageOnClickListener(imgBody.getLocalUrl(), imgBody.getRemoteUrl(), imageView);
                break;

            default:
                break;
        }
        if (statusSendStart != null && statusSendSucceed != null && statusSendFailed != null) {
            hideStatusViews(statusSendStart, statusSendFailed, statusSendSucceed);
            setSendFailedBtnListener(statusSendFailed, msg);
            switch (msg.status) {
                case FAIL:
                    statusSendFailed.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    //	statusSendSucceed.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS:
                    statusSendStart.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
        return conView;
    }

    private void setImageOnClickListener(final String path, final String url, final ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //	Intent intent = new Intent(ctx, PopuImgViewActivity.class);
                Intent intent = popuImgViewActivityIntent;
                intent.putExtra("path", path);
                intent.putExtra("url", url);
                intent.putExtra("originUrl", "");
                ctx.startActivity(intent);
            }
        });
    }

    private void setSendFailedBtnListener(View statusSendFailed, final EMMessage msg) {
        statusSendFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopApp.getInstance().getChatService().resendMsg(msg);
                notifyDataSetChanged();
            }
        });
    }

    private void hideStatusViews(View statusSendStart, View statusSendFailed, View statusSendSucceed) {
        statusSendFailed.setVisibility(View.GONE);
        statusSendStart.setVisibility(View.GONE);
        statusSendSucceed.setVisibility(View.GONE);
    }

    public View createViewByType(int itemViewType) {
        int[] types = new int[]{MsgViewType.COME_TEXT, MsgViewType.TO_TEXT, MsgViewType.COME_IMAGE, MsgViewType.TO_IMAGE, MsgViewType.COME_AUDIO, MsgViewType.TO_AUDIO};
        int[] layoutIds = new int[]{R.layout.chat_item_msg_text_left, R.layout.chat_item_msg_text_right, R.layout.chat_item_msg_img_left, R.layout.chat_item_msg_img_right, R.layout.chat_item_msg_text_left, R.layout.chat_item_msg_text_right};
        int i;
        for (i = 0; i < types.length; i++) {
            if (itemViewType == types[i]) {
                break;
            }
        }
        return inflater.inflate(layoutIds[i], null);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public void setToChatUserInfo(UserInfo toChatUserInfo) {
        this.toChatUserInfo = toChatUserInfo;
    }

}
