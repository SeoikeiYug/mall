package com.genius.mall.dao;

import com.genius.mall.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 搜索系统中的商品管理自定义Dao
 * Created by genius on 2019/12/01.
 */
public interface EsProductDao {

    List<EsProduct> getAllEsProductList(@Param("id") Long id);

}
