package com.genius.mall.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.genius.mall.common.api.CommonResult;
import com.genius.mall.service.RedisService;
import com.genius.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @类名 UmsMemberServiceImpl
 * @描述 会员管理Service实现类
 * @作者 shuaiqi
 * @创建日期 2019/10/29 16:03
 * @版本号 1.0
 * @参考地址
 **/
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

	@Autowired
	private RedisService redisService;

	@Value("${redis.key.prefix.authCode}")
	private String REDIS_KEY_PREFIX_AUTH_CODE;

	@Value("${redis.key.expire.authCode}")
	private Long AUTH_CODE_EXPIRE_SECONDS;

	@Override
	public CommonResult generateAuthCode(String telephone) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		//验证码绑定手机号并存储到redis
		redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
		redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
		return CommonResult.success(sb.toString(), "获取验证码成功");
	}


	//对输入的验证码进行校验
	@Override
	public CommonResult verifyAuthCode(String telephone, String authCode) {
		if (StringUtils.isEmpty(authCode)) {
			return CommonResult.failed("请输入验证码");
		}
		String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
		boolean result = authCode.equals(realAuthCode);
		if (result) {
			return CommonResult.success(null, "验证码校验成功");
		} else {
			return CommonResult.failed("验证码不正确");
		}
	}
}