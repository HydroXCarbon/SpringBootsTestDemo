package com.backend.model;

import lombok.Data;

@Data
public class MRegisterRequest {
    private String username;
    private String password;
    private String email;
}
