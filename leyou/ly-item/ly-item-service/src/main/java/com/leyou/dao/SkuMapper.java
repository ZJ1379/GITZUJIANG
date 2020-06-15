package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SkuMapper extends Mapper<Sku> {

    @Select("select * from tb_sku where spu_id=#{id} and enable=1")
    List<Sku> findSkusBySpuId(Long id);
}
