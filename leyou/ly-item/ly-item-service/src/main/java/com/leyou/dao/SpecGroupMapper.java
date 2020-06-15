package com.leyou.dao;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SpecGroupMapper  extends tk.mybatis.mapper.common.Mapper<SpecGroup> {

    @Select("select * from tb_spec_param where group_id=#{gid}")
    List<SpecParam> findParamByGroupId(Long gid);
    @Insert("insert into tb_spec_param values(null,#{cid},#{groupId},#{name},#{numeric},#{unit},#{generic},#{searching},#{segments}) ")
    void addParamByGroupId(SpecParam specParam);
    @Delete("delete from tb_spec_param where id = #{id}")
    void deleteParamById(Long id);
    @Update("update tb_spec_param set cid=#{cid},group_id=#{groupId},`name`=#{name},`numeric`=#{numeric},unit=#{unit},generic=#{generic},searching=#{searching},segments=#{segments} where id=#{id} ")
    void editParamByGroupId(SpecParam specParam);
}
