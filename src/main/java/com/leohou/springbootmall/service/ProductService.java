package com.leohou.springbootmall.service;

import com.leohou.springbootmall.controller.ProductRequest;
import com.leohou.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
