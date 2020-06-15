package com.leyou.controller;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private SpuClient spuClient;
    @Autowired
    private SkuClient skuClient;
    @Autowired
    private ParamClient paramClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;
    /**
     * 请求商品详情的微服务
     * spu
     * spudetail
     * sku
     * 三级分类
     * 规格参数详情
     * 规格参数组
     * @param spuId
     * @param model
     * @return 啊啊啊
     */
    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId") Long spuId, Model model){

        //spu
        Spu spu = spuClient.findSpuBySpuId(spuId);
        model.addAttribute("spu",spu);
        //spuDetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuId);
        model.addAttribute("spuDetail", spuDetail);
        //sku
        List<Sku> skuList = skuClient.findSkusBySpuId(spuId);
        model.addAttribute("skuList",skuList);
        //规格组
        List<SpecGroup> groupList = paramClient.findAllSpecGroup(spu.getCid3());
        model.addAttribute("groupList",groupList);
        //三级分类
        List<Category> categoryList = categoryClient.findCategoryByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        model.addAttribute("categoryList",categoryList);
        //规格参数
        List<SpecParam> specParamList = paramClient.findSpecparamByCidAndSearching(spu.getCid3(), 0);

        //规格参数的特殊属性
        Map<Long,String> paramMap = new HashMap<>();
        specParamList.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });
        model.addAttribute("paramMap",paramMap);
        //品牌
        Brand brand = brandClient.findBrandByBrandId(spu.getBrandId());
        model.addAttribute("brand",brand);
        return "item";
    }
}
