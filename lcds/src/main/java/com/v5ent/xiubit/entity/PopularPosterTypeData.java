package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by yangLin on 2018/1/7.
 */

public class PopularPosterTypeData extends BaseEntity {
    private static final long serialVersionUID = 5512230205316149823L;


        private List<TypeListBean> typeList;

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class TypeListBean {
            /**
             * name : 推荐
             * typeValue : 1
             */

            private String name;
            private String typeValue;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTypeValue() {
                return typeValue;
            }

            public void setTypeValue(String typeValue) {
                this.typeValue = typeValue;
            }
        }
    }
