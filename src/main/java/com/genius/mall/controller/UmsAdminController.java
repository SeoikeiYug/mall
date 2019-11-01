package com.genius.mall.controller;

import com.genius.mall.common.api.CommonResult;
import com.genius.mall.dto.UmsAdminLoginParam;
import com.genius.mall.mbg.model.UmsAdmin;
import com.genius.mall.mbg.model.UmsPermission;
import com.genius.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名 UmsAdminController
 * @描述 后台用户管理
 * @作者 shuaiqi
 * @创建日期 2019/11/1 10:09
 * @版本号 1.0
 * @参考地址
 **/

@Api(tags = "UmsAdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

	@Autowired
	private UmsAdminService adminService;

	@Value("${jwt.tokenHeader}")
	private String tokenHeader;

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	@ApiOperation(value = "用户注册")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
		UmsAdmin umsAdmin = adminService.register(umsAdminParam);
		if (umsAdmin == null) {
			CommonResult.failed();
		}
		return CommonResult.success(umsAdmin);
	}

	@ApiOperation(value = "登录以后返回token")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
		String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
		if (token == null) {
			return CommonResult.validateFailed("用户名或密码错误");
		}
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("token", token);
		tokenMap.put("tokenHead", tokenHead);
		return CommonResult.success(tokenMap);
	}

	@ApiOperation("获取用户所有权限（包括+-权限）")
	@RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
	public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId) {
		List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
		return CommonResult.success(permissionList);
	}
}