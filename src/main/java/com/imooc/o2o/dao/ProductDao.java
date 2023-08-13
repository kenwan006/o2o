package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Product;

public interface ProductDao {
	/**
	 * query the product and paging, input condition includes: fuzzy product name, product state, shop id, product category
	 * 
	 * @param productCondition
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * query the total count of product under certain condition
	 * 
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);

	/**
	 * search the product by the product id
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductById(long productId);

	/**
	 * insert a product
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * update product info
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);

	/**
	 * For the product from this product category, set their category to null
	 * 
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);

	/**
	 * delete product
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProduct(@Param("productId") long productId, @Param("shopId") long shopId);
}
