package com.leohou.springbootmall.dao;

import com.leohou.springbootmall.dto.ProductRequest;
import com.leohou.springbootmall.dto.ProductQueryParams;
import com.leohou.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

	List<Product> getProducts(ProductQueryParams productQueryParams);

	Product getProductById(Integer productId);

	Integer createProduct(ProductRequest productRequest);

	void updateProduct(Integer productId, ProductRequest productRequest);

	void deleteProduct(Integer productId);

	Integer countProduct(ProductQueryParams productQueryParams);

	void updateStock(Integer productId, Integer stock);
}
