package cn.hangzhou.liuxx.superworld.service;

import cn.hangzhou.liuxx.superworld.bean.response.SensitiveRuleResponse;
import cn.hangzhou.liuxx.superworld.common.PageResult;

import java.io.IOException;

public interface SensitiveIdentifyRuleService {

    PageResult<SensitiveRuleResponse> listRule(Integer pageIndex, Integer pageSize, Integer sensitiveType,
                                               Integer ruleType, Integer sensitiveLevel, Long startTime, Long endTime) throws IOException;
}
