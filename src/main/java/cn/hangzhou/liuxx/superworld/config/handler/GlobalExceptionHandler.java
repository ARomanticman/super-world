package cn.hangzhou.liuxx.superworld.config.handler;

import cn.hangzhou.liuxx.superworld.common.Result;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestControllerAdvice(basePackages = "cn.hangzhou.liuxx.superworld")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void systemException(HttpServletResponse response, Exception ex) {
        ex.printStackTrace();
        log.error("System inner error, exception is {}", ex.getMessage());
        Result result = new Result(500, ex.getMessage(), null);
        setResponse(response, 500, result);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public void runtimeException(HttpServletResponse response, RuntimeException ex) {
        ex.printStackTrace();
        log.error("runtime exception, error info is {}", ex.getMessage());
        Result result = new Result(500, ex.getMessage(), null);
        setResponse(response, 500, result);
    }

    private void setResponse(HttpServletResponse response, int statusCode, Result result) {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
