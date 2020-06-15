package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 查询
     * @param id
     * @return
     */
    public List<SpecGroup> findAllSpecGroup(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(id);

        //根据分类id查询规格参数组及组内的参数
        List<SpecGroup> groupList=new ArrayList<>();

        groupList=specGroupMapper.select(specGroup);

        groupList.forEach(group->{
            SpecParam param=new SpecParam();
            param.setGroupId(group.getId());
            group.setParams(specParamMapper.select(param));
        });
        return groupList;
    }

    /**
     * 添加
     * @param specGroup
     */
    public void addSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insertSelective(specGroup);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteSpecGroup(Long id) {

        specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改
     * @param specGroup
     */
    public void updateSpecGroup(SpecGroup specGroup) {

        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     * 查询规格
     * @param gid
     * @return
     */
    public List<SpecParam> findParamByGroupId(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setSearching(1);
        return specParamMapper.select(specParam);
    }

    public void addParamByGroupId(SpecParam specParam) {
        specGroupMapper.addParamByGroupId(specParam);
    }

    public void deleteParamById(Long id) {
        specGroupMapper.deleteParamById(id);
    }

    public void editParamByGroupId(SpecParam specParam) {
        specGroupMapper.editParamByGroupId(specParam);
    }

}
