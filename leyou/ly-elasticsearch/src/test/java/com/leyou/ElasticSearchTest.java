package com.leyou;

import com.leyou.client.SpuClient;
import com.leyou.common.PageResult;
import com.leyou.item.Goods;
import com.leyou.reponsitory.GoodsReponsitory;
import com.leyou.service.ElasticService;
import com.leyou.vo.SpuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private ElasticService elasticService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsReponsitory goodsReponsitory;


    @Test
    public void findAllSpu(){
        //创建索引库
        elasticsearchTemplate.createIndex(Goods.class);
        //映射
        elasticsearchTemplate.putMapping(Goods.class);

        PageResult<SpuVo> spuByPage = spuClient.findSpuByPage("", 1, 1, 200);
        spuByPage.getItems().forEach(
                i->{
                    System.out.println(i.getId());

                    try {
                        Goods goods = elasticService.convert(i);
                        goodsReponsitory.save(goods);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        );
    }
}
