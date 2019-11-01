package com.genius.mall.service;

import com.genius.mall.mbg.model.PmsBrand;

import java.util.List;

/**
 * @类名 PmsBrandService
 * @描述 PmsBrandService
 * @作者 shuaiqi
 * @创建日期 2019/10/29 15:27
 * @版本号 1.0
 * @参考地址
 **/
public interface PmsBrandService {

	List<PmsBrand> listAllBrand();

	int createBrand(PmsBrand brand);

	int updateBrand(Long id, PmsBrand brand);

	int deleteBrand(Long id);

	List<PmsBrand> listBrand(int pageNum, int pageSize);

	PmsBrand getBrand(Long id);

}
