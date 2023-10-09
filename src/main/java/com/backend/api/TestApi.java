package com.backend.api;

import com.backend.business.TestBusiness;
import com.backend.exception.BaseException;
import com.backend.model.MRegisterRequest;
import com.backend.model.TestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestApi {

    private final TestBusiness business;

    public TestApi(TestBusiness business) {
        this.business = business;
    }

    @GetMapping
    public TestResponse test() {

        TestResponse response = new TestResponse();
        response.setName("Hi");
        response.setAge(3);

        return response;
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<String> testRegister(@RequestBody MRegisterRequest request) throws BaseException, JsonProcessingException {
        String response = null;
        response = business.register(request);
        return ResponseEntity.ok("recieve: " + response);
    }
}
