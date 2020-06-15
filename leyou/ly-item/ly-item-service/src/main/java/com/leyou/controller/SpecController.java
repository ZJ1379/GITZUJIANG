package com.leyou.controller;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecGroupService specGroupService;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Autowired
    private SpecParamService specParamService;

    /**
     * 规格参数组查询
     * @param id
     * @return
     */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findAllSpecGroup(@PathVariable("cid") Long id){

        return specGroupService.findAllSpecGroup(id);
    }

    /**
     * 添加规格参数组
     * @param specGroup
     */
    @RequestMapping("group")
    public void addOrEditSpecGroup(@RequestBody SpecGroup specGroup){
        if (specGroup.getId()==null){
            specGroupService.addSpecGroup(specGroup);
        }else{
            specGroupService.updateSpecGroup(specGroup);
        }

    }

    /**
     * 删除规格参数组
     * @param id
     */
    @RequestMapping("group/{id}")
    public void deleteSpecGroup(@PathVariable("id") Long id){

        specGroupService.deleteSpecGroup(id);
    }

    /**
     * 根据规格组查询规格
     * @param gid
     * @return
     */

    @RequestMapping("params")
    public List<SpecParam> findParamByGroupId(@RequestParam("gid") Long gid){
        List<SpecParam> specParamList=specGroupService.findParamByGroupId(gid);
       return  specParamList;
    }


    /**
     * 添加规格
     * @param specParam
     */
    @RequestMapping("param")
    public void addParamByGroupId(@RequestBody SpecParam specParam){

        if (specParam.getId()==null){
            specGroupService.addParamByGroupId(specParam);
        }else {
            specGroupService.editParamByGroupId(specParam);
        }
    }

    /**
     * 删除规格表
     * @param id
     */
    @RequestMapping("param/{id}")
    public void deleteParamById(@PathVariable("id") Long id){
        specGroupService.deleteParamById(id);
    }



    @RequestMapping("paramByCid")
    public List<SpecParam> findSpecParamByCid(@RequestParam("cid") Long cid){

        return specParamMapper.findSpecParamByCid(cid);
    }

    /**
     * 根据cid和generic查询规格参数
     * @param cid
     * @param generic
     * @return
     */
    @RequestMapping("findSpecparamByCidAndSearching")
    public List<SpecParam> findSpecparamByCidAndSearching(@RequestParam("cid") Long cid,
                                                          @RequestParam("generic") Integer generic){
       return  specParamService.findSpecparamByCidAndSearching(cid,generic);
    }
}
