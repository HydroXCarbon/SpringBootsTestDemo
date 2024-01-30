package com.backend.business;

import com.backend.Util.SecurityUtil;
import com.backend.entity.User;
import com.backend.exception.BaseException;
import com.backend.exception.FileException;
import com.backend.exception.UserException;
import com.backend.mapper.UserMapper;
import com.backend.model.MLoginRequest;
import com.backend.model.MLoginResponse;
import com.backend.model.MRegisterRequest;
import com.backend.model.MRegisterResponse;
import com.backend.service.TokenService;
import com.backend.service.UserService;
import com.example.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class UserBusiness {

    private final UserService userService;

    private final UserMapper userMapper;

    private final TokenService tokenService;

    private final EmailBusiness emailBusiness;

    public UserBusiness(UserService userService, UserMapper userMapper, TokenService tokenService, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;
    }

    public MLoginResponse login(MLoginRequest request) throws BaseException{
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

        MLoginResponse response = new MLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName(), token);
        sendEmail(user);
        return userMapper.toMRegisterResponse(user);
    }

    private void sendEmail(User user){
        String token = user.getToken();

        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        }catch (BaseException e) {
            log.error(e.getMessage());
        }
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
