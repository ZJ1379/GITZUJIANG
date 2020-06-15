package com.leyou.client;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SpuVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("spu")
public interface SpuSearch {
    @RequestMapping("page")
    public PageResult<SpuVo> findSpuByPage(@RequestParam("key") String key,
                                           @RequestParam("saleable") Integer saleable,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows);
    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    @RequestMapping("findSpuBySpuId")
    public Spu findSpuBySpuId(@RequestParam("spuId") Long spuId);
}
