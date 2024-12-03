package com.sinfolix.Sai_Samarth.ENUM;

public enum StatusEnum {
    ORDER_COMPLETED(1), ORDER_PLACED(2), ORDER_REGECTED(3);

    private final int status;

    StatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}
