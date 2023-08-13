	package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	
	/**
	 * look up the product category by the shop id
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**
	 * add a batch of new product category
	 * 
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * delete the specified product category
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return effectedNum
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
