package com.leyou.client;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/category")
public interface CategorySearch {

    @RequestMapping("findCategoryByCid")
    public Category findCategoryByCid(@RequestParam("categoryId") Long categoryId);

    /**
     * 根据cid循环查询分类的名称
     * @param cids
     * @return
     */
    @RequestMapping("findCategoryByCids")
    public List<Category> findCategoryByCids(@RequestBody List<Long> cids);
}
