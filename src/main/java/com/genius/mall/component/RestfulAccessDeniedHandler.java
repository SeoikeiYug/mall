package com.genius.mall.component;

import cn.hutool.json.JSONUtil;
import com.genius.mall.common.api.CommonResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @类名 RestfulAccessDeniedHandler
 * @描述 当访问接口没有权限时，自定义的返回结果
 * @作者 shuaiqi
 * @创建日期 2019/11/1 9:49
 * @版本号 1.0
 * @参考地址
 **/
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(e.getMessage())));
		response.getWriter().flush();
	}
}