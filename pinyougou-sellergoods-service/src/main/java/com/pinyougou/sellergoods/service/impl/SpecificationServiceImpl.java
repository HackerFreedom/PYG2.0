package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		System.out.println("--无条件查询所有列表-service层--");
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		System.out.println("--分页查询所有列表-service层--");
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		System.out.println("--查询的page数据--:"+page);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
	    //从封装好的实体类中,可以取出规格和规格列表信息
		System.out.println("--执行添加信息的service层--");
	    //1.插入规格信息
        specificationMapper.insert(specification.getSpecification());

        //2. 循环遍历list集合,递归插入
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();

        for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
        //因为规格属性表中的spec_id是和规格表关联的,所i在mapper 中查询的时候,设置了一个<selectKey> 返回一个id.
            tbSpecificationOption.setSpecId(specification.getSpecification().getId());
            specificationOptionMapper.insert(tbSpecificationOption);
        }
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		System.out.println("---修改specification信息--");
		//1.保存修改的规格 (primarykey和 example的方法有什么区别.)
		specificationMapper.updateByPrimaryKey(specification.getSpecification());
		//2. 删除原来的规格选项
		TbSpecificationOptionExample example =new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		//指定规格为id的条件
		criteria.andSpecIdEqualTo(specification.getSpecification().getId());
		//拼接条件,删除信息
		specificationOptionMapper.deleteByExample(example);
		//3.然后在插入改变的信息
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
			//因为规格属性表中的spec_id是和规格表关联的,所i在mapper 中查询的时候,设置了一个<selectKey> 返回一个id.
			tbSpecificationOption.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(tbSpecificationOption);
		}
		
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		System.out.println("--查询specification的信息显示到页面--");
		
		Specification specification=new Specification();
		//1.获取规格实体
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		//1.1 封装到传输的桥梁类中去
		specification.setSpecification(tbSpecification);
		System.out.println("规格信息:"+tbSpecification);
		//2.获取规格选项列表
		//2.1获取到每一个框的id,可以通过实体的id来查询,specification属性中的设置值
		//TbSpecificationOption tbSpecificationOption = specificationOptionMapper.selectByPrimaryKey(tbSpecification.getId());
		//由于查询数据是要封装到集合中的,所以要根据条件来查询
		TbSpecificationOptionExample example =new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		//criteria.andIdEqualTo(tbSpecification.getId());  //规格属性和类型的信息:[]
		criteria.andSpecIdEqualTo(id);
		//将拼接好的条件来查询,数据
		List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);
		//封装信息
		System.out.println("规格属性和类型的信息:"+tbSpecificationOptions);
		specification.setSpecificationOptionList(tbSpecificationOptions);
		return specification;//组合实体类
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//删除规格表数据
			specificationMapper.deleteByPrimaryKey(id);
			System.out.println("删除的规格的id信息:"+id);
			//删除规格选项表数据		
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			//匹配的条件,这些条件我要怎么去理解?
			criteria.andSpecIdEqualTo(id);
			System.out.println("删除规格相关的属性和列表信息:"+example);
			specificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
			System.out.println("--页面加载数据的service层--");
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);
			System.out.println("页面的数据为:"+page);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 规格下拉列表的展示
	 * @return
	 */
		@Override
		public List<Map> selectOptionList() {
			System.out.println("--规格下拉列表service层--");
			return  specificationMapper.selectOptionList();
		}
	
}
