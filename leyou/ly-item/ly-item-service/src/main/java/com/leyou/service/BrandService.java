package com.leyou.service;

import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

//    public PageResult<Brand> findBrandByKey(String key, Integer page, Integer rows, String sortBy, boolean desc) {
//
//        PageHelper.startPage(page,rows);
//
//        List<Brand> brandList=brandMapper.findBrandByKey(key,sortBy,desc);
//
//        PageInfo<Brand> pageInfo=new PageInfo<Brand>(brandList);
//
//        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
//
//    }

    public void addBrand(Brand brand, List<Long> cids) {
        //添加品牌并返回主键值
        brandMapper.insert(brand);
        //添加中间表tb_category_brand
        cids.forEach(id->{
            brandMapper.addCategoryBrand(brand.getId(),id);
        });

    }

    public PageResult<Brand> findBrandByKey(String key, Integer page, Integer rows, String sortBy, boolean desc) {

        List<Brand> brandList=brandMapper.findAllBrand(key,(page-1)*rows,rows,sortBy,desc);

        Long total=brandMapper.findCountBrand(key,sortBy,desc);

        return new PageResult<Brand>(total,brandList);

    }


    public void deleteById(Long id) {
        //删除brand表
        Brand b =new Brand();
        b.setId(id);
        brandMapper.deleteByPrimaryKey(b);
        //删除关系表
        brandMapper.deleteBrandAndCategory(id);

    }

    public List<Category> findCategoryByBrandId(Long id) {
        return brandMapper.findCategoryByBrandId(id);
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌表
        brandMapper.updateByPrimaryKey(brand);
        //修改品牌和分类的表关系
        brandMapper.deleteBrandAndCategory(brand.getId());

        cids.forEach(id->{
            brandMapper.addCategoryBrand(brand.getId(),id);
        });
    }

    public List<Brand> findBrandByCategoryId(Long cid) {
       return  brandMapper.findBrandByCategoryId(cid);
    }

    public Brand findBrandByBrandId(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }
}
