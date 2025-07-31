package cn.hangzhou.liuxx.superworld.controller;

import cn.hangzhou.liuxx.superworld.bean.entity.EnumDataEntity;
import cn.hangzhou.liuxx.superworld.common.Result;
import cn.hangzhou.liuxx.superworld.service.EnumDataService;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enum/data")
@Slf4j
@Api(value = "枚举数据管理", tags = "枚举数据管理", description = "枚举数据管理API")
public class EnumDataController {

    @Autowired
    private EnumDataService enumDataService;

    @Value("${app.description}")
    private String description;

    @ApiOperation("获取所有数据类型")
    @GetMapping("/list/all_type")
    public Result<List<String>> getEnumDataType(){
        log.info("获取所有数据类型...");
        return Result.ok(enumDataService.listAllType());
    }

    @ApiOperation("获取指定类型下的数据，默认查询所有数据")
    @GetMapping("/list/data")
    public Result<List<EnumDataEntity>> getEnumData(
            @RequestParam(value = "type", required = false) String type){
        log.info(Strings.isNullOrEmpty(type) ? "获取所有数据" : "获取类型为" + type + "的数据");
        return Result.ok(enumDataService.listDataByType(type));
    }

    @ApiOperation("添加数据")
    @PostMapping("/add")
    public Result<Boolean> addEnumData(@RequestBody EnumDataEntity entity){
        log.info("添加数据, body:{}", entity);
        return Result.ok(enumDataService.addEnumData(entity));
    }

    @ApiOperation("删除数据")
    @PostMapping("/delete")
    public Result<Boolean> deleteEnumData(@RequestParam("id") String id){
        log.info("删除数据, id:{}", id);
        return Result.ok(enumDataService.deleteEnumData(id));
    }

    @ApiOperation("更新数据")
    @PostMapping("/update")
    public Result<Boolean> updateEnumData(@RequestBody EnumDataEntity entity){
        log.info("更新数据, body:{}", entity);
        return Result.ok(enumDataService.updateEnumData(entity));
    }

    @ApiOperation("读取Apollo配置项")
    @GetMapping("/apollo")
    public Result<String> getApolloConfig(){
        log.info("读取Apollo配置项");
        return Result.ok(description);
    }
}
