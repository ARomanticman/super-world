package cn.hangzhou.liuxx.superworld.controller;

import cn.hangzhou.liuxx.superworld.service.DynamicDatasourceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/dynamic/datasource")
@Api(value = "动态数据源", tags = "动态数据源", description = "动态数据源API")
public class DynamicDatasourceController {

    @Autowired
    private DynamicDatasourceService dynamicDatasourceService;

    @GetMapping("/super_world")
    public String getSuperWorldData(){
        return dynamicDatasourceService.getSuperWorldData();
    }

    @GetMapping("/component_manager")
    public String getComponentManagerData(){
        return dynamicDatasourceService.getComponentData();
    }

    @GetMapping("/mixed")
    public String getMixedData(){
        return dynamicDatasourceService.getMixedData();
    }

    @GetMapping("/dubbo")
    public String getDubboData(){
        return dynamicDatasourceService.saveTheWorld();
    }
}
