package com.leyou.service;

import com.leyou.dao.CategoryMapper;

import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /*
        查询所有
     */
    public List<Category> findAllCategory(Category category){

        return categoryMapper.select(category);
    }
    /*
        添加品牌
     */
    public void addCategory(Category category) {
        categoryMapper.insert(category);
    }


    /*
        修改品牌
     */
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
    /*
        删除品牌
     */
    public void deleteById(Long id) {
        Category category=new Category();
        category.setId(id);
        categoryMapper.deleteByPrimaryKey(category);
    }

    public Category findCategoryByCid(Long categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    public List<Category> findCategoryByCids(List<Long> cids) {
        List<Category> categoryList=new ArrayList<>();
        cids.forEach(cid->{
           categoryList.add(categoryMapper.selectByPrimaryKey(cid));
        });
        return categoryList;
    }
}
