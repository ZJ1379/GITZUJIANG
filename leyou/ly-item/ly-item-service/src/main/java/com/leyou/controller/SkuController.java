package com.leyou.controller;

import com.leyou.pojo.Sku;
import com.leyou.service.SkuService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 保存商品的方法
     * @param spuVo
     */
    @RequestMapping("addOrSaveSku")
    public void addOrSaveSku(@RequestBody SpuVo spuVo){
        if(spuVo.getId()==null){
            skuService.addSku(spuVo);
        }else{
            skuService.editSku(spuVo);
        }


    }

    /**
     * 根据spuId查询商品
     * @param id
     * @return
     */
    @RequestMapping("list")
    public List<Sku> findSkusBySpuId(@RequestParam("id") Long id){

        return skuService.findSkusBySpuId(id);
    }

    /**
     * 删除Sku及其他表根据spuId
     * @param spuId
     */
    @RequestMapping("deleteSkuBySpuId/{spuId}")
    public void deleteSkuBySpuId(@PathVariable("spuId") Long spuId){

        skuService.deleteSkuBySpuId(spuId);
    }
    /**
     * 上下架功能
     * @param saleable
     * @param spuId
     */
    @RequestMapping("upOrDownSaleableBySpuId")
    public void upOrDownSaleableBySpuId(@RequestParam("saleable") int saleable,
                                        @RequestParam("spuId") Long spuId){
        skuService.upOrDownSaleableBySpuId(saleable,spuId);

    }


}
