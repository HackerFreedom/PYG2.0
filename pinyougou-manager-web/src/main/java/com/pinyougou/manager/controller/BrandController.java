package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    //远程注入Reference
    @Reference
    private BrandService brandService;

    /**
     * 查询所有商品的品牌，为什么是在运营商后台管理？？？
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAllBrand() {
        List<TbBrand> brandList = brandService.findAll();
        return brandList;
    }

    /**
     * 分页返回所有的信息
     */
    @RequestMapping("/findPage")
    public PageResult findPages(int page, int size) {
        //调用service层的方法
        return brandService.findPage(page, size);
        //返回给请求端

    }

    /**
     * 添加brand的信息
     */
    @RequestMapping("/addBrand")
    public Result save(@RequestBody TbBrand tbBrand) {
        System.out.println("调用了save中的controller");
        //调用service层
        try {
            System.out.println("----控制器调用方法---");
            Exception add = brandService.add(tbBrand);

            System.out.println(add);
            if (add!=null){
                //有异常
                return new Result("添加失败", 1);
            }else {
                //正常
                return new Result("添加成功", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("添加失败", 1);
        }
    }

    /**
     * 先通过id,获取实体的信息
     */
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id) {
    //调用service层
        TbBrand tbBrand = brandService.findOne(id);
        return tbBrand;
    }
    /**
     * 修改数据
     */
    @RequestMapping("/updateBrand")
    public Result update(@RequestBody TbBrand tbBrand){
        try {
            System.out.println("--更新数据-控制器---");
            brandService.update(tbBrand);
            return new Result("恭喜欧巴变身成功",0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("可惜你错过我了",1);
        }
    }

    /**
     *批量删除选择的id,信息
     */
    @RequestMapping("/deleteBrand")
    public Result delete(Long[] ids){
        //调用service层
        System.out.println("---调用删除的控制器--");
        try {
            brandService.delete(ids);
            return new Result("你怎么会这么狠心呀",0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("看你p不p",1);
        }

    }
    /**
     * 查询条件加上分页
     *
     */
    @RequestMapping("/searchBrand")
    public PageResult search(@RequestBody  TbBrand tbBrand ,int page,int rows){
        System.out.println("--查询条件的控制层--");
         return brandService.findPage(tbBrand, page, rows);
    }
    /**
     * 品牌下拉列表展示
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        System.out.println("下拉列表展示--控制层");
        return brandService.selectOptionList();
    }
}
