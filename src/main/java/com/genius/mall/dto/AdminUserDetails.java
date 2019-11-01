package com.genius.mall.dto;

import com.genius.mall.mbg.model.UmsAdmin;
import com.genius.mall.mbg.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @类名 AdminUserDetails
 * @描述 SpringSecurity需要的用户详情
 * @作者 shuaiqi
 * @创建日期 2019/10/31 23:06
 * @版本号 1.0
 * @参考地址
 **/
public class AdminUserDetails implements UserDetails {

	private UmsAdmin umsAdmin;

	private List<UmsPermission> permissionList;

	public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
		this.umsAdmin = umsAdmin;
		this.permissionList = permissionList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//返回当前用户的权限
		return permissionList.stream()
				.filter(permission -> permission.getValue()!=null)
				.map(permission ->new SimpleGrantedAuthority(permission.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return umsAdmin.getPassword();
	}

	@Override
	public String getUsername() {
		return umsAdmin.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return umsAdmin.getStatus().equals(1);
	}

}