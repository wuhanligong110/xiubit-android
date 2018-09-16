package com.lcds.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.ErrorResponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/30
 */

public class Test {

    @org.junit.Test
    public void compare() {
//        boolean idCard18 = RegexUtils.isIDCard18("96242911230101690X");
        Gson gson = new Gson();
        String emptyJson = gson.toJson(new BaseResponseDemp<BaseEntity>());
        String json = "{\"code\":\"0\",\"msg\":\"success\",\"data\":null}";
        Type quickType = new TypeToken<BaseResponseEntity<BaseEntity>>() {
        }.getType();
        BaseResponseEntity<BaseEntity> ifastBaseRespons = gson.fromJson(json, quickType);
        System.out.print(4 % 2);
//        assertEquals(1,1);
    }

    public class BaseResponseDemp<T> extends BaseEntity {

        private String code = "0";

        private String msg = "success";

        private List<ErrorResponse> errors;

        private T data = null;
    }
}
