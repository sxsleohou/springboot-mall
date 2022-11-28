package com.leohou.springbootmall.controller;

import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product _product = productService.getProductById(productId);

        if( _product != null){
            return ResponseEntity.status(HttpStatus.OK).body(_product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId =  productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                               @RequestBody @Valid ProductRequest productRequest){
//        檢查 product 使否存在
        Product product = productService.getProductById(productId);

        if(null == product){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

//        修改商品的數據
        productService.updateProduct(productId, productRequest);

        product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
