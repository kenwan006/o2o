package com.imooc.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// convert page number to row number in database, and get the product list
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		// total count of product
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	// 1. process thumbnail img, get the relative path of the img and assign it to product
	// 2. add product info to the product
	// 3. process the product images in batch using the productId
	// 4. add the detailed images of the product to tb_product_img
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// check if the product is null
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// set time value
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// set the product state to be valid
			product.setEnableStatus(1);
			// thumbnail image of the product cannot be null
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// add new product
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("Unsuccessful to create new product");
				}
			} catch (Exception e) {
				throw new ProductOperationException("Successful to create new product:" + e.toString());
			}
			// add if the detailed image of product if null
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// return error message if the input param is empty
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	// 1. Process the thumbnail if it's not null
	//    Delete thumbnail if it exits, and then create new one. And get the relative path of the thumbnail and assign it to product
	// 2. Same process if the product image list is not null
	// 3. Remove from the tb_product_img all the images that belongs to the specified product
	// 4. update tb_product_img and tb_product 
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
		// check if the product is null
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// add the edit time to the product
			product.setLastEditTime(new Date());
			// Delete thumbnail if it exits, and then create new one
			if (thumbnail != null) {
				// get the original info of the product because it has all images address
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			// If the new image list is not null, then remove the original images list and add the new one
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}
			try {
				// update product info
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("Unsuccessful to update product");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("Unsuccessful to update product:" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * add thumbnail image 
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * add the images in batch
	 * 
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		// get the image path
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		// add all images to the productImage list 
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// if there is more than one image added
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("Unsuccessful to add product images");
				}
			} catch (Exception e) {
				throw new ProductOperationException("Unsuccessful to add product images: " + e.toString());
			}
		}
	}

	/**
	 * delete all the images of a specified product
	 * 
	 * @param productId
	 */
	private void deleteProductImgList(long productId) {
		// get the images using the productId
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		// delete all the images saved in local
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		// delete all the image info the database
		productImgDao.deleteProductImgByProductId(productId);
	}
}
