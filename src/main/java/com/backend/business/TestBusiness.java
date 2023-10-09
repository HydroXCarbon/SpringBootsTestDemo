package com.backend.business;

import com.backend.exception.BaseException;
import com.backend.exception.UserException;
import com.backend.model.MRegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TestBusiness {
    ObjectMapper objectMapper = new ObjectMapper();

    public String register(MRegisterRequest request) throws BaseException, JsonProcessingException {
        if (request == null) {
            throw UserException.requestNull();
        }

        //validate email
        if (Objects.isNull(request.getEmail())) {
            throw UserException.emailNull();
        }

        //validate username
        if (Objects.isNull(request.getUsername())) {
            throw UserException.usernameNull();
        }

        //validate password
        if (Objects.isNull(request.getPassword())) {
            throw UserException.passwordNull();
        }

        String requestJson = objectMapper.writeValueAsString(request);

        return requestJson;
    }
}
