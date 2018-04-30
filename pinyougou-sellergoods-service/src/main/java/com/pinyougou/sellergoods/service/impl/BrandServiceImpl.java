package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;

import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        System.out.println("ddddd");
        return brandMapper.selectByExample(null);
    }

    //分页查询
    @Override
    public PageResult findPage(int pages, int size) {
        System.out.println("--分页查询service层---");
        //Mybatis分页的插件,PageHelper

        PageHelper.startPage(pages, size);
        System.out.println("---前台传过来的当前页码:"+pages);
        System.out.println("---前台传过来的当前页的数据:"+size);
        List<TbBrand> brands = brandMapper.selectByExample(null);

        //因为我们要获取page
        Page<TbBrand> page = (Page<TbBrand>) brands;
        //将结果封装的到工具类中,分页的工具
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
      /*  pageResult.setRows();
        pageResult.setTotal(page.getTotal());*/

        System.out.println("返回给前台-page当前页数(list集合):" + page.getResult());
        System.out.println("返回给前台--rows总记录数:" + page.getTotal());
//还是觉得通过构造方法,来调用方法更代码更简洁
        return pageResult;
    }

    //添加brand的方法
    @Override
    public Exception add(TbBrand tbBrand) {
        //通过创建离线查询的方式来,获取brand根据条件查询

        TbBrandExample tbBrandExample = new TbBrandExample(); //扩展类,自动拼装的查询条件
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        //根据用户名来查询,自动拼接查询条件
        criteria.andNameEqualTo(tbBrand.getName());
        List<TbBrand> list = brandMapper.selectByExample(tbBrandExample);
        //说明它,查询到了数据,所以抛异常
        if (!list.isEmpty()) {
            System.out.println(list);
            //说明已经存在这个name,抛出异常
            System.out.println("已经存在name这个值");
            /**
             * 要自定义一个异常出来,然后返回给,controller层
             */
           return  new Exception("别闹,好好玩,小心点保护好人家");
            // new Result("别闹,好好玩",0,0);
        } else {
            //调用dao层,添加的方法
            System.out.println("--添加数据--:" + tbBrand);
            brandMapper.insert(tbBrand);
            //由于添加没有给前端一个友好的提示,所以这里我们可以将结果封装成一个类
            //解决重复的功能..先去数据库查,同名的名称,throw一个异常,,或者唯一约束
            return null;
        }
    }

    /**
     * 要完成修改,首先要根据id查询到信息
     * 显示出来
     *
     * @param id
     * @return
     */
    @Override
    public TbBrand findOne(Long id) {
        //调用service层处理数据
        System.out.println("--查询Brand--id数据");
        TbBrand tbBrand = brandMapper.selectByPrimaryKey(id);
        //在前端处理数据
        return tbBrand;
    }

    /**
     * 点击保存的时候,就更新数据
     *
     * @param tbBrand
     */
    @Override
    public void update(TbBrand tbBrand) {

        TbBrandExample tbBrandExample = new TbBrandExample(); //扩展类,自动拼装的查询条件
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        //根据用户名来查询,自动拼接查询条件
        criteria.andNameEqualTo(tbBrand.getName());
        List<TbBrand> list = brandMapper.selectByExample(tbBrandExample);
        //比较这个值存在的化,它返回就是一个空数组list
        System.out.println(list);
        if (!list.isEmpty()) {
            System.out.println("更新失败,信息重复");
        } else {
            System.out.println("--执行更新操作-调用dao--");
            brandMapper.updateByPrimaryKey(tbBrand);
        }

    }

    /**
     * 批量删除和删除
     *
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        //调用dao,删除的方法
        System.out.println("-dao层-根据id来删除:" + ids);
        for (Long id : ids) {
            //递归删除
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 根据关键字来搜索,分页数据
     *
     * @param tbBrand
     * @param pageNum  当前页码
     * @param pageSize 当前页的记录数
     * @return
     */
    @Override
    public PageResult findPage(TbBrand tbBrand, int pageNum, int pageSize) {
        System.out.println("--执行service层的分页查询--");
        //1.调用模版来,获取page所需要的数据
        PageHelper.startPage(pageNum, pageSize);

        //2.创建扩展类,有条件的查询数据
        TbBrandExample tbBrandExample = new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        if (tbBrand != null) {
            //创建一个模糊查询条件
            if (tbBrand.getName()!=null && tbBrand.getName().length()>0){
                System.out.println("--模糊查询--");
                criteria.andNameLike("%" + tbBrand.getName() + "%");
            }
            //第二的查询条件
            if (tbBrand.getFirstChar() !=null && tbBrand.getFirstChar().length()>0){
                System.out.println("--首字母匹配查询--");
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
            }
        }

        System.out.println("--执行dao层查询--");
        //执行查询,条件
        List<TbBrand> tbBrands = brandMapper.selectByExample(tbBrandExample);
        //转化为page对象
        Page<TbBrand> page= (Page<TbBrand>) tbBrands;
        System.out.println(page);
        return new PageResult(page.getTotal(),page.getResult());
    }

}
