package com.leohou.springbootmall.dao.impl;

import com.leohou.springbootmall.controller.ProductRequest;
import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String _sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "From Product with(nolock) where product_id = :productId";

        Map<String, Object> _map = new HashMap<String, Object>();
        _map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(_sql, _map, new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String _sql = "Insert Into product(product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date) " +
                "Values (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate) ";

        Map<String, Object> _map = new HashMap<String, Object>();
        _map.put("productName", productRequest.getProductName());
        _map.put("category", productRequest.getCategory().toString());
        _map.put("imageUrl", productRequest.getImageUrl());
        _map.put("price", productRequest.getPrice());
        _map.put("stock", productRequest.getStock());
        _map.put("description", productRequest.getDescription());

        Date date = new Date();
        _map.put("createdDate", date);
        _map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(_sql, new MapSqlParameterSource(_map), keyHolder);

        int _productId = keyHolder.getKey().intValue();

        return _productId;
    }
}
