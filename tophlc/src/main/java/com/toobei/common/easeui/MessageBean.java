package com.toobei.common.easeui;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class MessageBean  extends BaseEntity{


    private static final long serialVersionUID = 430586605618975459L;
    /**
     * weichat : {"agent":{"avatar":"//kefu-prod-avatar.img-cn-hangzhou.aliyuncs.com/avatar/13522/5cbe20ff-8943-46ef-85dd-bb12af6a47e8@219-219-585-585a|300h_300w|.png","userNickname":"小会"},"msgId":"a2854b5e-48aa-46c1-90cf-bdef6c23c3c4","queueName":"lcds","visitor":{"callback_user":"cfpc1add0a7c3e2438f853ae270e975d958","description":"来源android貅比特1.0.0.223","phone":"18782985332","trueName":"青烨宸","userNickname":"青烨宸"}}
     */

    private ExtBean ext;
    /**
     * bodies : [{"msg":"[(*)]","type":"txt"}]
     * ext : {"weichat":{"agent":{"avatar":"//kefu-prod-avatar.img-cn-hangzhou.aliyuncs.com/avatar/13522/5cbe20ff-8943-46ef-85dd-bb12af6a47e8@219-219-585-585a|300h_300w|.png","userNickname":"小会"},"msgId":"a2854b5e-48aa-46c1-90cf-bdef6c23c3c4","queueName":"lcds","visitor":{"callback_user":"cfpc1add0a7c3e2438f853ae270e975d958","description":"来源android貅比特1.0.0.223","phone":"18782985332","trueName":"青烨宸","userNickname":"青烨宸"}}}
     * from : toobeiCustomerServiceImAccount
     * to : cfpc1add0a7c3e2438f853ae270e975d958
     */

    private String from;
    private String to;
    /**
     * msg : [(*)]
     * type : txt
     */

    private List<BodiesBean> bodies;

    public ExtBean getExt() {
        return ext;
    }

    public void setExt(ExtBean ext) {
        this.ext = ext;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<BodiesBean> getBodies() {
        return bodies;
    }

    public void setBodies(List<BodiesBean> bodies) {
        this.bodies = bodies;
    }

    public static class ExtBean   extends BaseEntity{


        private static final long serialVersionUID = -1630601920610265230L;
        /**
         * agent : {"avatar":"//kefu-prod-avatar.img-cn-hangzhou.aliyuncs.com/avatar/13522/5cbe20ff-8943-46ef-85dd-bb12af6a47e8@219-219-585-585a|300h_300w|.png","userNickname":"小会"}
         * msgId : a2854b5e-48aa-46c1-90cf-bdef6c23c3c4
         * queueName : lcds
         * visitor : {"callback_user":"cfpc1add0a7c3e2438f853ae270e975d958","description":"来源android貅比特1.0.0.223","phone":"18782985332","trueName":"青烨宸","userNickname":"青烨宸"}
         */

        private WeichatBean weichat;

        public WeichatBean getWeichat() {
            return weichat;
        }

        public void setWeichat(WeichatBean weichat) {
            this.weichat = weichat;
        }

        public static class WeichatBean {
            /**
             * avatar : //kefu-prod-avatar.img-cn-hangzhou.aliyuncs.com/avatar/13522/5cbe20ff-8943-46ef-85dd-bb12af6a47e8@219-219-585-585a|300h_300w|.png
             * userNickname : 小会
             */

            private AgentBean agent;
            private String msgId;
            private String queueName;
            /**
             * callback_user : cfpc1add0a7c3e2438f853ae270e975d958
             * description : 来源android貅比特1.0.0.223
             * phone : 18782985332
             * trueName : 青烨宸
             * userNickname : 青烨宸
             */

            private VisitorBean visitor;

            public AgentBean getAgent() {
                return agent;
            }

            public void setAgent(AgentBean agent) {
                this.agent = agent;
            }

            public String getMsgId() {
                return msgId;
            }

            public void setMsgId(String msgId) {
                this.msgId = msgId;
            }

            public String getQueueName() {
                return queueName;
            }

            public void setQueueName(String queueName) {
                this.queueName = queueName;
            }

            public VisitorBean getVisitor() {
                return visitor;
            }

            public void setVisitor(VisitorBean visitor) {
                this.visitor = visitor;
            }

            public static class AgentBean {
                private String avatar;
                private String userNickname;

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getUserNickname() {
                    return userNickname;
                }

                public void setUserNickname(String userNickname) {
                    this.userNickname = userNickname;
                }
            }

            public static class VisitorBean {
                private String callback_user;
                private String description;
                private String phone;
                private String trueName;
                private String userNickname;

                public String getCallback_user() {
                    return callback_user;
                }

                public void setCallback_user(String callback_user) {
                    this.callback_user = callback_user;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getTrueName() {
                    return trueName;
                }

                public void setTrueName(String trueName) {
                    this.trueName = trueName;
                }

                public String getUserNickname() {
                    return userNickname;
                }

                public void setUserNickname(String userNickname) {
                    this.userNickname = userNickname;
                }
            }
        }
    }

    public static class BodiesBean {
        private String msg;
        private String type;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
