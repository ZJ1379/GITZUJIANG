package com.leyou.client;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("spec")
public interface ParamSearch {

    @RequestMapping("paramByCid")
    public List<SpecParam> findSpecParamByCid(@RequestParam("cid") Long cid);

    /**
     * 规格参数组查询
     * @param id
     * @return
     */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findAllSpecGroup(@PathVariable("cid") Long id);

    /**
     * 根据cid和generic查询规格参数
     * @param cid
     * @param generic
     * @return
     */
    @RequestMapping("findSpecparamByCidAndSearching")
    public List<SpecParam> findSpecparamByCidAndSearching(@RequestParam("cid") Long cid,
                                                          @RequestParam("generic") Integer generic);
}
