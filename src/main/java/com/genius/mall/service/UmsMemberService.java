package com.genius.mall.service;

import com.genius.mall.common.api.CommonResult;

/**
 * @类名 UmsMemberService
 * @描述 会员管理Service
 * @作者 shuaiqi
 * @创建日期 2019/10/29 15:27
 * @版本号 1.0
 * @参考地址
 **/
public interface UmsMemberService {

	/**
	 * 生成验证码
	 */
	CommonResult generateAuthCode(String telephone);

	/**
	 * 判断验证码和手机号码是否匹配
	 */
	CommonResult verifyAuthCode(String telephone, String authCode);

}
