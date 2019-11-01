package com.genius.mall.service;

/**
 * @类名 RedisService
 * @描述 redis操作Service, 对象和数组都以json形式进行存储
 * @作者 shuaiqi
 * @创建日期 2019/10/29 15:27
 * @版本号 1.0
 * @参考地址
 **/
public interface RedisService {

	/**
	 * 存储数据
	 */
	void set(String key, String value);

	/**
	 * 获取数据
	 */
	String get(String key);

	/**
	 * 设置超期时间
	 */
	boolean expire(String key, long expire);

	/**
	 * 删除数据
	 */
	void remove(String key);

	/**
	 * 自增操作
	 * @param delta 自增步长
	 */
	Long increment(String key, long delta);

}