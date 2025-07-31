package cn.hangzhou.liuxx.superworld.common;

import lombok.Data;

import java.util.List;

@Data
public class EsSQLResult {

    private List<Column> columns;

    private List<List<Object>> rows;

    private String cursor;

    @Data
    public static class Column {
        private String name;
        private String type;
    }
}
