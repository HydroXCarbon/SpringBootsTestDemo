package com.backend.model;

import lombok.Data;

@Data
public class MRegisterRequest {
    private String name;
    private String password;
    private String email;
}
