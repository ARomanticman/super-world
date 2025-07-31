package cn.hangzhou.liuxx.superworld.service;

import cn.hangzhou.liuxx.superworld.bean.entity.EnumDataEntity;

import java.util.List;

public interface EnumDataService {

    List<String> listAllType();

    List<EnumDataEntity> listDataByType(String type);

    Boolean addEnumData(EnumDataEntity entity);

    Boolean deleteEnumData(String id);

    Boolean updateEnumData(EnumDataEntity entity);
}
