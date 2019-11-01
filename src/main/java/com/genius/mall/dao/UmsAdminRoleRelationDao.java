package com.genius.mall.dao;

import com.genius.mall.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @类名 UmsAdminRoleRelationDao
 * @描述 后台用户与角色管理自定义Dao
 * @作者 shuaiqi
 * @创建日期 2019/10/31 22:59
 * @版本号 1.0
 * @参考地址
 **/
public interface UmsAdminRoleRelationDao {

	/**
	 * 获取用户所有权限(包括+-权限)
	 */
	List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);

}