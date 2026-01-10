package cn.hangzhou.liuxx.superworld.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共返回实体类
 * @author chenzb
 * @date 2018/6/29
 */
@Data
@ApiModel(value = "请求结果对象")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "响应码", required = true)
    private int errCode;

    @ApiModelProperty(value = "响应信息", required = true)
    private String message;

    @ApiModelProperty(value = "响应数据", required = true)
    private T data;

    public Result() {
    }

    public Result(int status, String message) {
        this(status, message, null);
    }

    public Result(HttpStatus status, String message) {
        this.errCode = status.getValue();
        this.message = message;
        this.data = data;
    }

    public Result(int status, String message, T data) {
        this.errCode = status;
        this.message = message;
        this.data = data;
    }

    public Result(HttpStatus status, String message, T data) {
        this.errCode = status.getValue();
        this.message = message;
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static Result ok() {
        return new Result<>(200, "请求成功", null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "请求成功", data);
    }

    public static Result error() {
        return new Result<>(400, "请求失败", null);
    }

    public static Result error(String message) {
        return new Result<>(400, message, null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "errCode=" + errCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
