package com.leohou.springbootmall.dao.impl;

import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.dto.ProductQueryParams;
import com.leohou.springbootmall.dto.ProductRequest;
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
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
//      MySQL
		/*
		String _sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
				"created_date, last_modified_date " +
				"From Product Where 1=1";
		*/
//		SQL Server

		String _sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
				"created_date, last_modified_date " +
				"From Product with(nolock) Where 1=1";


		Map<String, Object> _map = new HashMap<String, Object>();

//		查詢條件
		_sql = addFilteringSql(_sql, _map, productQueryParams);

//		排序
		_sql += " Order By " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

//		分頁
//		MySQL
		_sql += " Limit :limit OFFSET :offset ";
//		SQL Server
//		_sql += " OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";

		_map.put("offset", productQueryParams.getOffset());
		_map.put("limit", productQueryParams.getLimit());


		List<Product> productList = namedParameterJdbcTemplate.query(_sql, _map, new ProductRowMapper());

		return productList;
	}

	@Override
	public Product getProductById(Integer productId) {
//      MySQL
		String _sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
				"created_date, last_modified_date " +
				"From Product where product_id = :productId";

//      SQL Server
//		String _sql = "Select product_id, product_name, category, image_url, price, stock, description, " +
//				"created_date, last_modified_date " +
//				"From Product with(nolock) where product_id = :productId";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("productId", productId);

		List<Product> productList = namedParameterJdbcTemplate.query(_sql, _map, new ProductRowMapper());

		if (productList.size() > 0) {
			return productList.get(0);
		} else {
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

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		String _sql = "Update product set product_name=:productName, category=:category, " +
				"image_url=:imageUrl, price=:price, stock=:stock, description=:description, " +
				"last_modified_date=:lastModifiedDate " +
				"Where product_id=:productId ";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("productId", productId);

		_map.put("productName", productRequest.getProductName());
		_map.put("category", productRequest.getCategory().toString());
		_map.put("imageUrl", productRequest.getImageUrl());
		_map.put("price", productRequest.getPrice());
		_map.put("stock", productRequest.getStock());
		_map.put("description", productRequest.getDescription());

		_map.put("lastModifiedDate", new Date());

		namedParameterJdbcTemplate.update(_sql, new MapSqlParameterSource(_map));
	}

	@Override
	public void updateStock(Integer productId, Integer stock) {
		String _sql = "Update product set stock=:stock, last_modified_date=:lastModifiedDate " +
				"Where product_id=:productId ";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("productId", productId);

		_map.put("stock", stock);
		_map.put("lastModifiedDate", new Date());

		namedParameterJdbcTemplate.update(_sql, new MapSqlParameterSource(_map));
	}

	@Override
	public void deleteProduct(Integer productId) {
		String _sql = "Delete From product Where product_id=:productId ";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("productId", productId);

		namedParameterJdbcTemplate.update(_sql, new MapSqlParameterSource(_map));
	}

	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		String _sql = "Select count(*) total " +
				"From Product Where 1=1";

		Map<String, Object> _map = new HashMap<String, Object>();

//		查詢條件
		_sql = addFilteringSql(_sql, _map, productQueryParams);

		Integer total = namedParameterJdbcTemplate.queryForObject(_sql, _map, Integer.class);

		return total;
	}

	private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
		if (null != productQueryParams.getCategory()) {
			sql += " and category=:category";
			map.put("category", productQueryParams.getCategory().name());
		}
		if (null != productQueryParams.getSearch()) {
			sql += " and product_name like :search";
			map.put("search", "%" + productQueryParams.getSearch() + "%");
		}
		return sql;
	}

}
