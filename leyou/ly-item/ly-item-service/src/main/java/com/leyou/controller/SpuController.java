package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 商品集查询
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("page")
    public PageResult<SpuVo> findSpuByPage(@RequestParam("key") String key,
                                           @RequestParam("saleable") Integer saleable,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows){

        PageResult<SpuVo> pageResult = spuService.findSpuByPage(key, saleable, page, rows);
        return pageResult;
    }
    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){

        return spuService.findSpuDetailBySpuId(spuId);
    }

    /**
     * 根据SpuId查询spu表
     * @param spuId
     * @return
     */
    @RequestMapping("findSpuBySpuId")
    public Spu findSpuBySpuId(@RequestParam("spuId") Long spuId){
         return spuService.findSpuBySpuId(spuId);
    }



}
