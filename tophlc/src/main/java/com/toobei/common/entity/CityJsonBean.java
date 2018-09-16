package com.toobei.common.entity;

import java.util.List;

/**
 * Created by 11191 on 2018/6/5.
 */

public class CityJsonBean {


    public List<ProvicesBean> provices;

    public static class ProvicesBean {
        /**
         * value : 110000
         * text : 北京市
         * children : [{"value":"110101","text":"东城区"},{"value":"110102","text":"西城区"},{"value":"110103","text":"崇文区"},{"value":"110104","text":"宣武区"},{"value":"110105","text":"朝阳区"},{"value":"110106","text":"丰台区"},{"value":"110107","text":"石景山区"},{"value":"110108","text":"海淀区"},{"value":"110109","text":"门头沟区"},{"value":"110111","text":"房山区"},{"value":"110112","text":"通州区"},{"value":"110113","text":"顺义区"},{"value":"110114","text":"昌平区"},{"value":"110115","text":"大兴区"},{"value":"110116","text":"怀柔区"},{"value":"110117","text":"平谷区"},{"value":"110228","text":"密云县"},{"value":"110229","text":"延庆县"},{"value":"110230","text":"其它区"}]
         */

        public String value;
        public String text;
        public List<ChildrenBean> children;

        public static class ChildrenBean {
            /**
             * value : 110101
             * text : 东城区
             */

            public String value;
            public String text;
        }
    }
}
