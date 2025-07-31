package cn.hangzhou.liuxx.superworld.common;

import cn.hutool.core.util.PageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "分页")
public class PageResult<T> {
    private static final long serialVersionUID = 9056411043515781783L;
    @ApiModelProperty(value = "页面数，从1开始", required = true)
    private int page;
    @ApiModelProperty(value = "页面大小，默认10")
    private int pageSize;
    @ApiModelProperty(value = "总页面数")
    private int totalPage;
    @ApiModelProperty(value = "总数量", required = true)
    private Long total;
    @ApiModelProperty(value = "数据")
    private List<T> list;

    public PageResult() {
        this(0, 10);
    }

    public PageResult(int page, int pageSize) {
        this.page = Math.max(page, 0);
        this.pageSize = pageSize <= 0 ? 20 : pageSize;
    }

    public PageResult(int page, int pageSize, List<T> list, long total) {
        this(page, pageSize, total);
        this.list = list;
    }

    public PageResult(int page, int pageSize, Long total) {
        this(page, pageSize);
        this.total = total;
        this.totalPage = PageUtil.totalPage(total, pageSize);
    }
}
