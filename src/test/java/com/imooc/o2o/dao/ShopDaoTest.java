package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 1, 5);
		int count= shopDao.queryShopCount(shopCondition);
		System.out.println("size of shop list: " + shopList.size());
		System.out.println("count of total shops: " + count);
		
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		shopList= shopDao.queryShopList(shopCondition, 0, 2);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("size of new shop list: " + shopList.size());
		System.out.println("count of new total shops: " + count);
		
		
	}
	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId = 2;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId: " + shop.getArea().getAreaId());
		System.out.println("areaName: " + shop.getArea().getAreaName());
	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("test shop");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("under review");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(2L);
		shop.setShopDesc("test description");
		shop.setShopAddr("test address");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
	
}
