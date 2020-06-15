package com.leyou.service;

import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SkuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 添加Sku
     * @param spuVo
     */
    public void addSku(SpuVo spuVo) {

        Date date = new Date();
        /**
         * 添加商品集spu
         */
        Spu spu = new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setBrandId(spuVo.getBrandId());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(date);
        spu.setLastUpdateTime(date);

        spuMapper.insertSelective((SpuVo) spu);
        /**
         * 新增spuDetail
         */
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insertSelective(spuDetail);


        /**
         * 添加商品sku
         */
        spuVo.getSkus().forEach(sku ->{
            sku.setSpuId(spu.getId());
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insertSelective(sku);
            //添加库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insertSelective(stock);
        });


    }
    /**
     * 根据spuId查询商品
     * @param id
     * @return
     */
    public List<Sku> findSkusBySpuId(Long id) {
        List<Sku> skus = skuMapper.findSkusBySpuId(id);

            skus.forEach(sku->{
                Long skuId = sku.getId();
                Stock stock=stockMapper.findStockBySkuId(skuId);
                sku.setStock(stock.getStock());
            });
        return  skus;
    }

    /**
     * 修改Sku
     * @param spuVo
     */
    public void editSku(SpuVo spuVo) {

        Date date = new Date();
        //修改spu表
        spuVo.setValid(null);
        spuVo.setSaleable(null);
        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        //修改spuDetail
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        List<Sku> skuList = skuMapper.findSkusBySpuId(spuVo.getId());


        skuList.forEach(sku ->{
            //删除
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);
            stockMapper.deleteByPrimaryKey(sku.getId());
        });
        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku ->{
            sku.setSpuId(spuVo.getId());
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insertSelective(sku);
            //添加库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insertSelective(stock);
        });

    }

    /**
     * 删除sku表
     * @param spuId
     */
    public void deleteSkuBySpuId(Long spuId) {
       //删除sku表
        List<Sku> skuList = skuMapper.findSkusBySpuId(spuId);
        skuList.forEach(sku ->{
            //删除
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);
            stockMapper.deleteByPrimaryKey(sku.getId());
        });
        //删除Detail
        spuDetailMapper.deleteByPrimaryKey(spuId);
        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);
    }
    /**
     * 上下架功能
     * @param saleable
     * @param spuId
     */
    public  void upOrDownSaleableBySpuId(int saleable, Long spuId) {

        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable==1?true:false);
        spuMapper.updateByPrimaryKeySelective((SpuVo) spu);
    }

}
