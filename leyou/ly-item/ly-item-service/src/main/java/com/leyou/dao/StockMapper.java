package com.leyou.dao;

import com.leyou.pojo.Stock;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
@org.apache.ibatis.annotations.Mapper
public interface StockMapper extends Mapper<Stock> {

    @Select("select * from tb_stock where sku_id=#{skuId}")
    Stock findStockBySkuId(Long skuId);
}
