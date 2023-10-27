package com.backend.business;

import com.backend.entity.User;
import com.backend.exception.BaseException;
import com.backend.exception.FileException;
import com.backend.exception.UserException;
import com.backend.mapper.UserMapper;
import com.backend.model.MLoginRequest;
import com.backend.model.MRegisterRequest;
import com.backend.model.MRegisterResponse;
import com.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserBusiness(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public String login(MLoginRequest request) throws BaseException{
        // validate
        if( Objects.isNull(request.getEmail())){
            throw UserException.loginFailEmailNull();
        }

        if( Objects.isNull(request.getPassword())){
            throw UserException.loginFailPasswordNull();
        }

        //verify
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if(!userService.matchPassword(request.getPassword(), user.getPassword())){
            throw UserException.loginFailPasswordIncorrect();
        }

        // TODO:JWT
        String token = " JWT ";

        return token;
    }

    public MRegisterResponse register(MRegisterRequest request) throws BaseException {

        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());

        return  userMapper.toMRegisterResponse(user);
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
