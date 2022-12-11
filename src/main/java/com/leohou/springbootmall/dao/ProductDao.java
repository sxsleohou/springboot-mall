package com.leohou.springbootmall.dao;

import com.leohou.springbootmall.constant.ProductCategory;
import com.leohou.springbootmall.controller.ProductRequest;
import com.leohou.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

	List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
