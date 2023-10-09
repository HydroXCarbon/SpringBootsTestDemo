package com.backend.api;

import com.backend.business.MProductBusiness;
import com.backend.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductApi {

    private final MProductBusiness product;

    public ProductApi(MProductBusiness pruduct) {
        this.product = pruduct;
    }

    @GetMapping("/product{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String id)throws BaseException {
        String response = product.getProductById(id);
        return ResponseEntity.ok("productId: " + response);
    }
}

