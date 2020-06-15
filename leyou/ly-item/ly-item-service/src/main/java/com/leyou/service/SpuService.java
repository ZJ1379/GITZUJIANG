package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;



    public PageResult<SpuVo> findSpuByPage(String key, Integer saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);

        List<SpuVo> spuList=spuMapper.findSpuByPage(key,saleable);

        PageInfo<SpuVo> pageInfo = new PageInfo<SpuVo>(spuList);

        return new PageResult<SpuVo>(pageInfo.getTotal(),pageInfo.getList());

    }

    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    public SpuDetail findSpuDetailBySpuId(Long spuId) {

        return spuDetailMapper.selectByPrimaryKey(spuId);

    }

    public Spu findSpuBySpuId(Long spuId) {
        return spuMapper.selectByPrimaryKey(spuId);
    }
}
