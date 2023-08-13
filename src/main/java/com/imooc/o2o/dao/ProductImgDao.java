package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.ProductImg;

public interface ProductImgDao {

	/**
	 * list the image for a product
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);

	/**
	 * insert product image in batch
	 * 
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	/**
	 * delete all images of a specified product
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
