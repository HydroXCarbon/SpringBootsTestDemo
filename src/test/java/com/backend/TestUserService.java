package com.backend;

import com.backend.Util.SecurityUtil;
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
class TestUserService{

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

    @Order(1)
    @Test
    void testCreate() throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(TestCreateData.email, TestCreateData.password, TestCreateData.name, token);

        // Check not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());

        // Check equals
        Assertions.assertEquals(TestCreateData.name, user.getName());
        Assertions.assertEquals(TestCreateData.email, user.getEmail());

        boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
        Assertions.assertTrue(isMatched);

    }

    @Order(2)
    @Test
    void testUpdate() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        User updateUser = userService.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updateUser);
        Assertions.assertEquals(TestUpdateData.name, updateUser.getName());
    }

    @Order(3)
    @Test
    void testCreateSocial() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        Social social = user.getSocial();
        Assertions.assertNull(social);

        social = socialService.create(
                user,
                SocialTestCreateData.facebook,
                SocialTestCreateData.Line,
                SocialTestCreateData.instagram,
                SocialTestCreateData.tiktok
        );

        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());
    }

    @Order(4)
    @Test
    void testCreateAddress() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        List<Address> addresses = user.getAddresses();
        Assertions.assertTrue(addresses.isEmpty());

        createAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
        createAddress(user, AddressTestCreateData2.line1, AddressTestCreateData2.line2, AddressTestCreateData2.zipcode);
    }

    void createAddress(User user, String line1, String line2, String zipcode) throws BaseException {
        Address address = addressService.create(
                user,
                line1,
                line2,
                zipcode
        );
    }

    @Order(9)
    @Test
    void testDelete() {
        Optional<User> opt = userService.findByEmail(TestUpdateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        // Check Social
        Social social = user.getSocial();
        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());


        // Check Address
        List<Address> addresses = user.getAddresses();
        Assertions.assertFalse(addresses.isEmpty());
        Assertions.assertEquals(2, addresses.size());


        userService.deleteById(user.getId());

        Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(optDelete.isEmpty());

    }

    interface TestCreateData{

        String email = "test@test.com";

        String password = "123asdlkj!@#";

        String name = "Usertest name";
    }

    interface SocialTestCreateData{

        String facebook = "facebook.com";

        String Line = "Line.com";

        String instagram = "instagram.com";

        String tiktok = "tiktok.com";
    }

    interface TestUpdateData{

        String email = "test@test.com";

        String password = "123asdlkj!@#";

        String name = "Usertest name 1";
    }

    interface AddressTestCreateData{

            String line1 = "123/456";

            String line2 = "Thailand";

            String zipcode = "12345";
    }

    interface AddressTestCreateData2{

        String line1 = "789/456";

        String line2 = "Malaysia";

        String zipcode = "55555";
    }

}
