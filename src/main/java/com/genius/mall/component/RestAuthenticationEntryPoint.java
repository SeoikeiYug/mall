package com.genius.mall.component;

import cn.hutool.json.JSONUtil;
import com.genius.mall.common.api.CommonResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @类名 RestAuthenticationEntryPoint
 * @描述 当未登录或者token失效访问接口时，自定义的返回结果
 * @作者 shuaiqi
 * @创建日期 2019/11/1 9:47
 * @版本号 1.0
 * @参考地址
 **/
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().println(JSONUtil.parse(CommonResult.unauthorized(authException.getMessage())));
		response.getWriter().flush();
	}

}