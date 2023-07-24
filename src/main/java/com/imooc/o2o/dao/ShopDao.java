package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * find shop by shopId
	 * @param ShopId
	 * @return
	 */
	Shop queryByShopId(long ShopId);
	
	/**
	 * add a new shop
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * update shop info
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
}
 