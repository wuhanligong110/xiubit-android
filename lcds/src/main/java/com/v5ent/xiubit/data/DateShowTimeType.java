package com.v5ent.xiubit.data;

public enum DateShowTimeType {
    YREA(1), QUARTER(2), MONTH(3), DAY(4), WEEK(5), TOTAL(6);

    int value;

    DateShowTimeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DateShowTimeType fromInt(int i) {
        return values()[i - 1];
    }
}