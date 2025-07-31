package cn.hangzhou.liuxx.superworld.common;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "请求响应码")
public enum HttpStatus {
    OK(200),
    BAD_REQUEST(500);

    private int value;

    public int getValue() {
        return this.value;
    }

    private HttpStatus(int value) {
        this.value = value;
    }
}
