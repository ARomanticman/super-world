package cn.hangzhou.liuxx.superworld.provider.impl;

import cn.hangzhou.liuxx.superworld.api.SuperHeroServiceApi;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class SuperHeroServiceImpl implements SuperHeroServiceApi {

    @Override
    public String saveTheWorld() {
        return "衰小孩拯救世界";
    }
}
