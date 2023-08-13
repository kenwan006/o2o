package com.imooc.o2o.service;

import java.util.List;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * query product and paging, the input condition includes: fuzzy product name, product state, shopId, product category
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * query the product info by the product id
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);

	/**
	 * add product info and process image
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException;
	
	/**
	 * modify product info and process image
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException;

}

