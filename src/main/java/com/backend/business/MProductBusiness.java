package com.backend.business;

import com.backend.exception.BaseException;
import com.backend.exception.ProductException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MProductBusiness {
    public String getProductById(String id) throws BaseException {

        if(Objects.equals("111", id)){
            throw ProductException.notFound();
        }

        return id;
    }
}
