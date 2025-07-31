package cn.hangzhou.liuxx.superworld.controller;

import cn.hangzhou.liuxx.superworld.bean.response.SensitiveRuleResponse;
import cn.hangzhou.liuxx.superworld.common.PageResult;
import cn.hangzhou.liuxx.superworld.common.Result;
import cn.hangzhou.liuxx.superworld.service.SensitiveIdentifyRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sensitive_identify/labeling")
@Api(value = "敏感数据打标",tags = "敏感数据打标", description = "敏感数据打标API")
@Slf4j
public class SensitiveIdentifyController {

    @Autowired
    private SensitiveIdentifyRuleService sensitiveIdentifyRuleService;

    @ApiOperation(value = "分页条件查询 敏感数据识别规则")
    @GetMapping("/list/rule")
    public Result<PageResult<SensitiveRuleResponse>> listRule(
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sensitiveType", required = false) Integer sensitiveType,
            @RequestParam(value = "ruleType", required = false) Integer ruleType,
            @RequestParam(value = "sensitiveLevel", required = false) String sensitiveLevel,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime
    ){
        try{
            log.info("listRule, sensitiveType:{}, ruleType:{}, sensitiveLevel:{}, startTime:{}, endTime:{}",
                    sensitiveType, ruleType, sensitiveLevel, startTime, endTime);
            return Result.ok(sensitiveIdentifyRuleService.listRule(pageIndex, pageSize, sensitiveType, ruleType,
                    Integer.parseInt(sensitiveLevel), startTime, endTime));
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询敏感数据识别规则异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
