package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;

	@Test
	@Ignore
	public void testAddProduct() throws ShopOperationException, FileNotFoundException {
		// create a product with shopId=1 and productCategoryId=1
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("test_product1");
		product.setProductDesc("test_product1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		// create file stream for the thumbnail image
		File thumbnailFile = new File("/Users/chaowan/Pictures/minions.png");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// create two file streams for two images and add them to image list
		File productImg1 = new File("/Users/chaowan/Pictures/minions.png");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("/Users/chaowan/Pictures/baymax.png");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		// add product and verify
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}

	@Test
	@Ignore
	public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
		// create a product with shopId=5 and productCategoryId=1
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(5L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("Formal product");
		product.setProductDesc("Formal product");
		// Create file stream of the thumbnail image
		File thumbnailFile = new File("/Users/chaowan/Pictures/baymax.png");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// Create two file streams of the detail product image and add them to product image list
		File productImg1 = new File("/Users/chaowan/Pictures/QRcode.png");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("/Users/chaowan/Pictures/baymax.png");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		// add product and verify
		ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}

}
