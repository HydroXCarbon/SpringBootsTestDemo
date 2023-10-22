package com.backend.api;

import com.backend.business.UserBusiness;
import com.backend.entity.User;
import com.backend.exception.BaseException;
import com.backend.model.MRegisterRequest;
import com.backend.model.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;

    public UserApi(UserBusiness business) {
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
    public ResponseEntity<User> testRegister(@RequestBody MRegisterRequest request) throws BaseException {
        User response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException {
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }
}
