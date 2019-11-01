package com.genius.mall.service.impl;

import com.genius.mall.common.utils.JwtTokenUtil;
import com.genius.mall.dao.UmsAdminRoleRelationDao;
import com.genius.mall.mbg.mapper.UmsAdminMapper;
import com.genius.mall.mbg.model.UmsAdmin;
import com.genius.mall.mbg.model.UmsAdminExample;
import com.genius.mall.mbg.model.UmsPermission;
import com.genius.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @类名 UmsAdminServiceImpl
 * @描述 UmsAdminService实现类
 * @作者 shuaiqi
 * @创建日期 2019/10/31 22:56
 * @版本号 1.0
 * @参考地址
 **/
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UmsAdminMapper adminMapper;

	@Autowired
	private UmsAdminRoleRelationDao adminRoleRelationDao;

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	@Override
	public UmsAdmin getAdminByUsername(String username) {
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<UmsAdmin> adminList = adminMapper.selectByExample(example);
		if (adminList != null && adminList.size() > 0) {
			return adminList.get(0);
		}
		return null;
	}

	@Override
	public UmsAdmin register(UmsAdmin umsAdminParam) {
		UmsAdmin umsAdmin = new UmsAdmin();
		BeanUtils.copyProperties(umsAdminParam, umsAdmin);
		umsAdmin.setCreateTime(new Date());
		umsAdmin.setStatus(1);
		//查询是否有相同用户名的用户
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
		List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
		if (umsAdminList.size() > 0) {
			return null;
		}
		//将密码进行加密操作
		String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
		umsAdmin.setPassword(encodePassword);
		adminMapper.insert(umsAdmin);
		return umsAdmin;
	}

	@Override
	public String login(String username, String password) {
		String token = null;
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (!passwordEncoder.matches(password, userDetails.getPassword())) {
				throw new BadCredentialsException("密码不正确");
			}
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtTokenUtil.generateToken(userDetails);
		} catch (AuthenticationException e) {
			LOGGER.warn("登录异常:{}", e.getMessage());
		}
		return token;
	}

	@Override
	public List<UmsPermission> getPermissionList(Long adminId) {
		return adminRoleRelationDao.getPermissionList(adminId);
	}

}