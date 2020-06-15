package com.leyou.client;

import com.leyou.pojo.Sku;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("goods")
public interface SkuSearch {

    /**
     * 根据spuId查询商品
     * @param id
     * @return
     */
    @RequestMapping("list")
    public List<Sku> findSkusBySpuId(@RequestParam("id") Long id);
}
