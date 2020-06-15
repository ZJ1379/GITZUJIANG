package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    //查询所有品牌
    @RequestMapping("page")
    public PageResult<Brand> findAllBrand(@RequestParam("key") String key,
                               @RequestParam("page") Integer page,
                               @RequestParam("rows") Integer rows,
                               @RequestParam("sortBy") String sortBy,
                               @RequestParam("desc") boolean desc){
        PageResult<Brand> pageResult=brandService.findBrandByKey(key,page,rows,sortBy,desc);

        return pageResult;
    }
    //添加品牌
    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand ,@RequestParam("cids") List<Long> cids){
        if(brand.getId()==null){
            brandService.addBrand(brand,cids);
        }else{
            brandService.updateBrand(brand,cids);
        }


    }
    //删除功能
    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id){
        brandService.deleteById(id);
    }
    //修改查询
    @RequestMapping("bid/{id}")
    public List<Category> findCategoryByBrandId(@PathVariable("id") Long id){
        List<Category> categoryList=brandService.findCategoryByBrandId(id);

        return categoryList;
    }

    /**
     * 根据分类查询品牌
     * @param cid
     * @return
     */
    @RequestMapping("cid/{cid}")
    public List<Brand>findBrandByCategoryId(@PathVariable("cid") Long cid){

       return  brandService.findBrandByCategoryId(cid);
    }

    /**
     * 根据brandId查询品牌
     * @param brandId
     * @return
     */
    @RequestMapping("findBrandByBrandId")
    public Brand findBrandByBrandId(@RequestParam("brandId") Long brandId){
        return brandService.findBrandByBrandId(brandId);
    }


}
