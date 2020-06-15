package com.leyou.controller;


import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.common.PageResult;
import com.leyou.item.Goods;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.reponsitory.GoodsReponsitory;
import com.leyou.service.ElasticService;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ElasticService service;

    @Autowired
    private GoodsReponsitory goodsReponsitory;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @RequestMapping("page")
    public PageResult<Goods> page(@RequestBody SearchRequest searchRequest){

        //去es索引库查询
        NativeSearchQueryBuilder builder= new NativeSearchQueryBuilder();
        //根据all查询构造条件 or
        builder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        //分页查询
        builder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));
        //执行查询 totalelement 总条数 totalpage 总页数 content 数据内容
        Page<Goods> goods = goodsReponsitory.search(builder.build());
        //根据新品排序
        builder.withSort(SortBuilders.fieldSort(searchRequest.getSortBy()).order(searchRequest.isDescending()? SortOrder.DESC:SortOrder.ASC));
        //加载分类和品牌
        String categoryName="categoryName";
        String brandName="brandName";

        builder.addAggregation(AggregationBuilders.terms(categoryName).field("cid3"));

        builder.addAggregation(AggregationBuilders.terms(brandName).field("brandId"));
        //获取aggregations
        AggregatedPage<Goods> search= (AggregatedPage<Goods>) goodsReponsitory.search(builder.build());
        //构造分类信息--根据分类id获取名称
        LongTerms categoryAgg= (LongTerms) search.getAggregation(categoryName);

        List<Category> categoryList=new ArrayList<Category>();

        categoryAgg.getBuckets().forEach(bucket -> {
            Long categoryId=(Long) bucket.getKey();

            //根据分类Id去进行查询分类
            Category category = categoryClient.findCategoryByCid(categoryId);
            categoryList.add(category);

        });
        //构造品牌信息--根据品牌brandId查询品牌
        LongTerms brandAgg= (LongTerms) search.getAggregation(brandName);
        List<Brand> brandList=new ArrayList<Brand>();
        brandAgg.getBuckets().forEach(bucket -> {
               Long brandId= (Long) bucket.getKey();
           Brand brand = brandClient.findBrandByBrandId(brandId);
            brandList.add(brand);
        });

        return new SearchResult(goods.getTotalElements(), goods.getContent(), goods.getTotalPages(),categoryList,brandList);

    }

}
