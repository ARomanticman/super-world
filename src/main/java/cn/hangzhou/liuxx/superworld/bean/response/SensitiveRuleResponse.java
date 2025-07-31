package cn.hangzhou.liuxx.superworld.bean.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
@ApiModel("敏感数据识别规则")
public class SensitiveRuleResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("敏感数据类型名称")
    private String sensitiveTypeName;

    @ApiModelProperty("规则类型")
    private String identifyRuleType;

    @ApiModelProperty("规则内容")
    private String ruleContent;

    @ApiModelProperty("敏感级别")
    private String sensitiveLevel;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @Tolerate
    public SensitiveRuleResponse(){}
}
