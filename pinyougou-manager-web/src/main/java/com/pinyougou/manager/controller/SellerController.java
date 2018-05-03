package com.pinyougou.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller seller){
		try {
			sellerService.add(seller);
			return new Result("增加成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("增加失败", 1);
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
			return new Result("修改成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("修改失败", 1);
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new Result("删除成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("删除失败", 1);
		}
	}
	
		/**
	 * 查询+分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		System.out.println("--商家审核信息查询的控制台--");
		return sellerService.findPage(seller, page, rows);
	}
	/**
	 * 改变商家的状态值
	 *
	 */
	@RequestMapping("/updateStatus")
	public Result updateStatus(@RequestBody String sellerId,String status ){
		//调用service层的方法
		//友好显示,将成功的信息
		try {
			sellerService.updateStatus(sellerId,status);
			return new Result("更改成功",0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("更改失败",1);
		}

	}
}
