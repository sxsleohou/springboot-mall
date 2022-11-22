package com.leohou.springbootmall.dao.impl;

import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
}
