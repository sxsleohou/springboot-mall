package com.leohou.springbootmall.dao;

import com.leohou.springbootmall.controller.ProductRequest;
import com.leohou.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
