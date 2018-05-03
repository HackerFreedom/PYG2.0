package com.pinyougou.manager.controller;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

	@Reference
	private SpecificationService specificationService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll(){
		System.out.println("--无条件查询所有列表-控制器--");
		return specificationService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){
		System.out.println("--分页查询所有列表-控制器--");
		return specificationService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Specification specification){
		try {
			System.out.println("--添加信息-控制器--");
			specificationService.add(specification);
			return new Result("增加成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("增加失败", 1);
		}
	}
	
	/**
	 * 修改
	 * @param specification
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Specification specification){
		try {
			System.out.println("--修改信息的控制器--");
			specificationService.update(specification);
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
	public Specification findOne(Long id) {
		return specificationService.findOne(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			specificationService.delete(ids);
			return new Result("删除成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("删除失败", 1);
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSpecification specification, int page, int rows  ){
		System.out.println("--页面加载的数据每次刷新-控制层-");
		return specificationService.findPage(specification, page, rows);
	}
	/**
	规格下拉列表的展示
	 */
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		System.out.println("--规格下拉列表的展示-控制层-");
		return specificationService.selectOptionList();
	}
	
}
