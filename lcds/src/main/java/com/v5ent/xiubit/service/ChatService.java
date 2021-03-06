package com.v5ent.xiubit.service;

import android.content.Context;
import android.content.Intent;

import com.toobei.common.service.TopChatService;
import com.v5ent.xiubit.activity.ChatActivity;

public class ChatService extends TopChatService {

    private static ChatService service;

    private ChatService() {
        super();
    }

    public static synchronized ChatService getInstance() {
        if (service == null) {
            service = new ChatService();
        }
        return service;
    }

    @Override
    protected Intent getPendingIntent(Context context) {
        return new Intent(context, ChatActivity.class);
    }

}
