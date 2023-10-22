package com.backend.business;

import com.backend.entity.User;
import com.backend.exception.BaseException;
import com.backend.exception.FileException;
import com.backend.model.MRegisterRequest;
import com.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class UserBusiness {

    private final UserService userService;

    public UserBusiness(UserService userService) {
        this.userService = userService;
    }

    public User register(MRegisterRequest request) throws BaseException {
        User user = userService.Create(request.getEmail(), request.getPassword(), request.getName());

        //TODO : mapper

        return user;
    }

    public String uploadProfilePicture(MultipartFile file) throws BaseException {

        // validate file
        if (file == null) {
            throw FileException.fileNull();
        }

        // validate file size
        if (file.getSize() > 1048576 * 2) {
            throw FileException.fileMaxSize();
        }

        if (file.getContentType() == null) {
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/png", "image/jpeg");
        if (!supportedTypes.contains(file.getContentType())) {
            throw FileException.unsupported();
        }

        // store data
        try {
            byte[] bytes = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }
}
