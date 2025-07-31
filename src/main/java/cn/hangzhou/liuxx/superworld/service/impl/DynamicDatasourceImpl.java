package cn.hangzhou.liuxx.superworld.service.impl;

import cn.hangzhou.liuxx.superworld.api.SuperHeroServiceApi;
import cn.hangzhou.liuxx.superworld.dao.BrandManagerMapper;
import cn.hangzhou.liuxx.superworld.dao.EnumDataMapper;
import cn.hangzhou.liuxx.superworld.service.DynamicDatasourceService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DynamicDatasourceImpl implements DynamicDatasourceService {

    @Autowired
    private EnumDataMapper enumDataMapper;

    @Autowired
    private BrandManagerMapper brandManagerMapper;

    @DubboReference
    private SuperHeroServiceApi superHeroServiceApi;

    @Override
    public String getSuperWorldData() {
        List<String> allType = enumDataMapper.listAllType();
        return allType.get(0);
    }

    @Override
    @DS("component_manager")
    public String getComponentData() {
        String code = brandManagerMapper.getBrandCodeById("d0c28b91fd8448ae95496ddffcde8b95");
        return code;
    }

    @Override
    public String getMixedData() {
        String superWorldData = this.getSuperWorldData();
        DynamicDataSourceContextHolder.push("component_manager");
        String componentData = this.getComponentData();
        return superWorldData + " /|\\ " + componentData;
    }

    @Override
    public String saveTheWorld() {
        return superHeroServiceApi.saveTheWorld();
    }

}
