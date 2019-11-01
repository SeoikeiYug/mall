package com.genius.mall.controller;

import com.genius.mall.common.api.CommonResult;
import com.genius.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @类名 UmsMemberController
 * @描述 会员登录注册管理Controller
 * @作者 shuaiqi
 * @创建日期 2019/10/29 16:07
 * @版本号 1.0
 * @参考地址
 **/
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {

	@Autowired
	private UmsMemberService memberService;

	@ApiOperation("获取验证码")
	@RequestMapping(value = "/get-auth-code", method = RequestMethod.GET)
	public CommonResult getAuthCode(@RequestParam String telephone) {
		return memberService.generateAuthCode(telephone);
	}

	@ApiOperation("判断验证码是否正确")
	@RequestMapping(value = "/verify-auth-code", method = RequestMethod.POST)
	public CommonResult updatePassword(@RequestParam String telephone, @RequestParam String authCode) {
		return memberService.verifyAuthCode(telephone,authCode);
	}

}