package com.leohou.springbootmall.service.impl;

import com.leohou.springbootmall.dto.ProductRequest;
import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.dto.ProductQueryParams;
import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		return productDao.getProducts(productQueryParams);
	}

	@Override
	public Product getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		return productDao.createProduct(productRequest);
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		productDao.updateProduct(productId, productRequest);
	}

	@Override
	public void deleteProduct(Integer productId) {
		productDao.deleteProduct(productId);
	}

	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		return productDao.countProduct(productQueryParams);
	}
}
