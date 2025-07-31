package cn.hangzhou.liuxx.superworld.dao;

import cn.hangzhou.liuxx.superworld.bean.entity.EnumDataEntity;

import java.util.List;

public interface EnumDataMapper {

    List<String> listAllType();

    List<EnumDataEntity> listDataByType(String type);

    int insertEnumData(EnumDataEntity entity);

    int deleteById(String id);

    int updateById(EnumDataEntity entity);
}
