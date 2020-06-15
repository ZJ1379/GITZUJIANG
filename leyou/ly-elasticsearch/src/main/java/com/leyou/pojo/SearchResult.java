package com.leyou.pojo;

import com.leyou.common.PageResult;
import com.leyou.item.Goods;

import java.util.List;

public class SearchResult extends PageResult<Goods>{

    private List<Category> categoryList;

    private List<Brand> brandList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public SearchResult() {
    }

    public SearchResult(Long total, List<Goods> items, Integer totalPage) {
        super(total, items, totalPage);
    }

    public SearchResult(Long total) {
        super(total);
    }

    public SearchResult(Long total, List<Goods> items) {
        super(total, items);
    }

    public SearchResult(Long total, List<Goods> items, Integer totalPage, List<Category> categoryList, List<Brand> brandList) {
        super(total, items, totalPage);
        this.categoryList = categoryList;
        this.brandList = brandList;
    }
}
