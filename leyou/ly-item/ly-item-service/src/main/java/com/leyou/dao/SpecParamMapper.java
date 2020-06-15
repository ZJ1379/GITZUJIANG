package com.leyou.dao;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SpecParamMapper extends Mapper<SpecParam> {

    @Select("select p.* from tb_category c , tb_spec_param p where c.id =p.cid and c.id=#{cid}")
    List<SpecParam> findSpecParamByCid(Long cid);
}
