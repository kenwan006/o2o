package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	@Ignore
	public void testAInsertProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(1L);
		// Create three product entities and insert into the shop (shopId=1)
		// Set product category Id to 1
		Product product1 = new Product();
		product1.setProductName("test1");
		product1.setProductDesc("testDesc1");
		product1.setImgAddr("test1");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		Product product2 = new Product();
		product2.setProductName("test2");
		product2.setProductDesc("testDesc2");
		product2.setImgAddr("test2");
		product2.setPriority(2);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		Product product3 = new Product();
		product3.setProductName("test3");
		product3.setProductDesc("testDesc3");
		product3.setImgAddr("test3");
		product3.setPriority(3);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop1);
		product3.setProductCategory(pc1);
		// check if it has been added successfully
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product3);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testBQueryProductList() throws Exception {
		Product productCondition = new Product();
		// query in page, and expect three results to be returned
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, productList.size());
		// query the total count of product that with the name "test"
		int count = productDao.queryProductCount(productCondition);
		assertEquals(6, count);
		// use fuzzy name query, and expect 4 results to be returned
		productCondition.setProductName("test");
		productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, productList.size());
		count = productDao.queryProductCount(productCondition);
		assertEquals(4, count);
	}

	@Test
	@Ignore
	public void testCQueryProductByProductId() throws Exception {
		long productId = 1;
		//Initialize two product image for product (productId=1)
		//Insert them to the product
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("img1");
		productImg1.setImgDesc("test img1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("img2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
		// query the product info with productId=1, and return the size of the image list
		Product product = productDao.queryProductById(productId);
		assertEquals(2, product.getProductImgList().size());
		// delete two added product images
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
		
	}

	@Test
	@Ignore
	public void testDUpdateProduct() throws Exception {
		Product product = new Product();
		ProductCategory pc = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(1L);
		pc.setProductCategoryId(1L);
		product.setProductId(5L);
		product.setShop(shop);
		product.setProductName("first product");
		product.setProductCategory(pc);
		// change the name and category of product with productId =1
		// check if the effected row is 1
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
		
	}

	@Test
	@Ignore
	public void testEUpdateProductCategoryToNull() {
		// for all the product with product categoryId=2, set their product category to null
		int effectedNum = productDao.updateProductCategoryToNull(1L);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testFDeleteShopAuthMap() throws Exception {
		// delete the inserted product
		Product productCondition = new Product();
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		productCondition.setProductCategory(pc);
		// query the newly added three test data using the productCategoryId=1
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, productList.size());
		// delete these three data
		for (Product p : productList) {
			int effectedNum = productDao.deleteProduct(p.getProductId(), 1);
			assertEquals(1, effectedNum);
		}
	}
}
