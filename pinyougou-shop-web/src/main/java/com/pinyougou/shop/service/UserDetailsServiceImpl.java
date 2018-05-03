package com.pinyougou.shop.service;

import java.util.ArrayList;
import java.util.List;


import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义一个认证类
 *
 * @author Administrator
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	//通过配置文件来注入service
	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("经过了UserDetailsServiceImpl认证处理");
		//构建角色列表
		List<GrantedAuthority> grantAuths=new ArrayList();
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

		System.out.println("名字:"+username);
		//得到商家对象
		TbSeller seller = sellerService.findOne(username);
		System.out.println("获取的商家:"+seller.getPassword());
		System.out.println(seller.getStatus());
		if(seller!=null){
			if(seller.getStatus().equals("1")){
			    //说明通过审核
				return new User(username,seller.getPassword(),grantAuths);
			}else{

				return null;
			}			
		}else{
			return null;
		}
	}

}
