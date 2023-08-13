package com.imooc.o2o.service;


import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * return the shop list based on the page index and size
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	
	
	/**
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param FileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	
	/**
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param FileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
