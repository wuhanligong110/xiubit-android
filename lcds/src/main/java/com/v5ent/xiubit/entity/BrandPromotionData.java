package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/5
 */

public class BrandPromotionData extends BaseEntity {
    private static final long serialVersionUID = 2212845369902116675L;


        private String qrcode;
        private List<PosterListBean> posterList;

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public List<PosterListBean> getPosterList() {
            return posterList;
        }

        public void setPosterList(List<PosterListBean> posterList) {
            this.posterList = posterList;
        }

        public static class PosterListBean {
            /**
             * image : ae4ce77ba57944e63e797b9c998aa096
             * smallImage : 4a97fc8c050ce643aa374a602ab227f4
             */

            private String image;
            private String smallImage;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getSmallImage() {
                return smallImage;
            }

            public void setSmallImage(String smallImage) {
                this.smallImage = smallImage;
            }
        }
    }
