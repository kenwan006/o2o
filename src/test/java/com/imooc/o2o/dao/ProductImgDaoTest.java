package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testABatchInsertProductImg() throws Exception {
		// insert two product images to the product with productId=1
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("image1");
		productImg1.setImgDesc("testimage1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("image2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
	}

	@Test
	public void testBQueryProductImgList() {
		// verify if the product (productId=1) has two images
		List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
		assertEquals(2, productImgList.size());
	}

	@Test
	public void testCDeleteProductImgByProductId() throws Exception {
		// delete two added images
		long productId = 1;
		int effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
	}
}
