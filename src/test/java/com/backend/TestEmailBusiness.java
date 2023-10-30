package com.backend;

import com.backend.business.EmailBusiness;
import com.backend.entity.Address;
import com.backend.entity.Social;
import com.backend.entity.User;
import com.backend.exception.BaseException;
import com.backend.service.AddressService;
import com.backend.service.SocialService;
import com.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

    @Autowired
    private EmailBusiness emailBusiness;


    @Order(1)
    @Test
    void testSendActivateEmail() throws BaseException {
        emailBusiness.sendActivateUserEmail(TestData.email, TestData.name, TestData.token);
    }

    interface TestData {
        String email = "purin.pongpanich@gmail.com";

        String token = "!@#F&^*^&asd";

        String name = "Purin pongapnich";
    }
}

