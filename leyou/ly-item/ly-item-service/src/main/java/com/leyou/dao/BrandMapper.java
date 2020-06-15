package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {


    List<Brand> findBrandByKey(@Param("key") String key,
                               @Param("sortBy") String sortBy,
                               @Param("desc") boolean desc);

    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    void addCategoryBrand(Long bid, Long cid);


    List<Brand> findAllBrand(@Param("key")String key,
                             @Param("page")int page,
                             @Param("rows")Integer rows,
                             @Param("sortBy")String sortBy,
                             @Param("desc")boolean desc);

    Long findCountBrand(@Param("key")String key,
                        @Param("sortBy")String sortBy,
                        @Param("desc")boolean desc);
    @Delete("delete from tb_category_brand where  brand_id=#{id}")
    void deleteBrandAndCategory(Long id);
    @Select("select t.* from tb_category t,tb_category_brand y where y.category_id=t.id and y.brand_id=#{id}")
    List<Category> findCategoryByBrandId(Long id);
    @Select("select * from tb_brand b , tb_category_brand c where b.id=c.brand_id and c.category_id=#{cid}")
    List<Brand> findBrandByCategoryId(Long cid);
}
