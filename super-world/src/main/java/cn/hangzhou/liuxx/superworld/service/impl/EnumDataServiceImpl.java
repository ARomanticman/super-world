package cn.hangzhou.liuxx.superworld.service.impl;

import cn.hangzhou.liuxx.superworld.bean.entity.EnumDataEntity;
import cn.hangzhou.liuxx.superworld.dao.EnumDataMapper;
import cn.hangzhou.liuxx.superworld.service.EnumDataService;
import cn.hutool.core.util.IdUtil;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EnumDataServiceImpl implements EnumDataService {

    @Autowired
    private EnumDataMapper enumDataMapper;

    @Override
    public List<String> listAllType() {
        return enumDataMapper.listAllType();
    }

    @Override
    public List<EnumDataEntity> listDataByType(String type) {
        List<String> allTypes = this.listAllType();
        if(!allTypes.contains(type)){
            throw new RuntimeException("不存在的数据类型: " + type);
        }
        return enumDataMapper.listDataByType(type);
    }

    @Override
    public Boolean addEnumData(EnumDataEntity entity) {
        if(entity.getId() == null){
            entity.setId(IdUtil.simpleUUID());
        }
        if(Strings.isNullOrEmpty(entity.getType())){
            throw new RuntimeException("数据类型不能为空");
        }
        return enumDataMapper.insertEnumData(entity) > 0;
    }

    @Override
    public Boolean deleteEnumData(String id) {
        return enumDataMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean updateEnumData(EnumDataEntity entity) {
        return enumDataMapper.updateById(entity) > 0;
    }


}
