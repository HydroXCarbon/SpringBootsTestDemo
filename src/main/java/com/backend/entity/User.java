package com.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name="m_user")

public class User extends BaseEntity {

    @Column(name="Email", nullable = false, unique = true, length = 60)
    private String email;

    @JsonIgnore
    @Column(name="Password", nullable = false, length = 120)
    private String password;

    @Column(name="Name", nullable = false, unique = true, length = 120)
    private String name;

}
