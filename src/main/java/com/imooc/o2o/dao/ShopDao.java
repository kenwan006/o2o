package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	
	/**
	 * find a shop list, the input includes: fuzzy shop name, shop category, area id and owner
	 * @param shopCondition
	 * @param rowIndex starting index
	 * @param pageSize  number of shops returned
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * return the total number in the shop list
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	
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
 