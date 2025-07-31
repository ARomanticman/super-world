package cn.hangzhou.liuxx.superworld.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EsSQLParam {

    private String query;

    private List<Object> params = new ArrayList<>();

    public void addParam(Object param) {
        params.add(param);
    }
}
