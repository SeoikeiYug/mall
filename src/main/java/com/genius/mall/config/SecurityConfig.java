package com.genius.mall.config;

import com.genius.mall.component.JwtAuthenticationTokenFilter;
import com.genius.mall.component.RestAuthenticationEntryPoint;
import com.genius.mall.component.RestfulAccessDeniedHandler;
import com.genius.mall.dto.AdminUserDetails;
import com.genius.mall.mbg.model.UmsAdmin;
import com.genius.mall.mbg.model.UmsPermission;
import com.genius.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @类名 SecurityConfig
 * @描述 SpringSecurity的配置
 * @作者 shuaiqi
 * @创建日期 2019/10/31 22:02
 * @版本号 1.0
 * @参考地址
 **/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UmsAdminService umsAdminService;

	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
				.disable()
				.sessionManagement()// 基于token，所以不需要session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
						"/",
						"/*.html",
						"/favicon.ico",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js",
						"/swagger-resources/**",
						"/v2/api-docs/**"
				)
				.permitAll()
				.antMatchers("/admin/login", "/admin/register")// 对登录注册要允许匿名访问
				.permitAll()
				.antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
				.permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
				.anyRequest()// 除上面外的所有请求全部需要鉴权认证
				.authenticated();
		// 禁用缓存
		httpSecurity.headers().cacheControl();
		// 添加JWT filter
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//添加自定义未授权和未登录结果返回
		httpSecurity.exceptionHandling()
				.accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthenticationEntryPoint);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		//获取登录用户信息
		return username -> {
			UmsAdmin admin = umsAdminService.getAdminByUsername(username);
			if (admin != null) {
				List<UmsPermission> permissionList = umsAdminService.getPermissionList(admin.getId());
				return new AdminUserDetails(admin, permissionList);
			}
			throw new UsernameNotFoundException("用户名或密码错误");
		};
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}