package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌服务接口
 */
public interface BrandService {
    /*
      *简单的查询所有的品牌信息
     */
   List<TbBrand> findAll();

    /**
     *品牌分页
     * pageNum  当前记录
     * pageSize 每页的记录数
     */
    PageResult findPage(int pageNum,int pageSize);

    /**
     * 添加品牌
     */
   Exception add(TbBrand tbBrand);

    /**
     * 根据ID查询实体信息
     */
    TbBrand findOne(Long id);

    /**
     * 修改信息
     */
    void update(TbBrand tbBrand);

    /**
     * 删除信息
     * Long[] ids
     */
    void delete(Long[] ids);

    /**
     * 更加条件,关键子,分页查询数据
     * brand , pageNume, pageSize
     */
    PageResult findPage(TbBrand tbBrand,int pageNum,int pageSize);

    /**
     *  品牌下拉列表展示
     */
    List<Map> selectOptionList();
}
