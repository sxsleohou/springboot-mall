package com.leohou.springbootmall.service.impl;

import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
