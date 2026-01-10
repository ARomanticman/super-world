package cn.hangzhou.liuxx.superworld.controller;

import cn.hangzhou.liuxx.superworld.service.MiddlewareService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/middleware")
@Api(value = "中间件",tags = "中间件", description = "中间件API")
@Slf4j
public class MiddlewareController {

    @Autowired
    private MiddlewareService middlewareService;

    @RequestMapping(value = "/add/redis")
    public void addRedis(@Param("key") String key, @Param("value") String value){
        middlewareService.addToRedis(key, value);
    }

    @RequestMapping(value = "/get/redis")
    public Object getRedis(@Param("key") String key){
        return middlewareService.getFromRedis(key);
    }
}
