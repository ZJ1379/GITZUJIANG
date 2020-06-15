package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    /*
        查询所有
     */
    @RequestMapping("list")
    public List<Category> findAllCategory(@RequestParam("pid") Long pid){
        Category category = new Category();
        category.setParentId(pid);

        List<Category> categoryList = categoryService.findAllCategory(category);

        return categoryList;
    }
    /*
        添加品牌
     */
    @RequestMapping("add")
    public String addCategory(@RequestBody Category category){
        String result="SUCC";
        try {
            categoryService.addCategory(category);
            return result;
        }catch (Exception e){
            System.out.println("添加失败了！！");
            return "FAIL";
        }
    }
    /*
        修改品牌
     */
    @RequestMapping("update")
    public String updateCategory(@RequestBody Category category){
        String result="SUCC";
        try {
            categoryService.updateCategory(category);
            return result;
        }catch (Exception e){
            return result="FAIL";
        }
    }
    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id") Long id){
        String result="SUCC";
        try {
            categoryService.deleteById(id);
            return result;
        }catch (Exception e){
            return result="FAIL";
        }

    }

    /**
     * 根据cid查询分类对象
     * @param categoryId
     * @return
     */
    @RequestMapping("findCategoryByCid")
    public Category findCategoryByCid(@RequestParam("categoryId") Long categoryId){
        return categoryService.findCategoryByCid(categoryId);
    }

    /**
     * 根据cid循环查询分类的名称
     * @param cids
     * @return
     */
    @RequestMapping("findCategoryByCids")
    public List<Category> findCategoryByCids(@RequestBody List<Long> cids){
       return  categoryService.findCategoryByCids(cids);
    }

}
