package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name="m_user")
public class User extends BaseEntity {

    @Column(name="Email", nullable = false, unique = true, length = 60)
    private String email;

    @Column(name="Password", nullable = false, length = 120)
    private String password;

    @Column(name="Name", nullable = false, unique = true, length = 120)
    private String name;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Social social;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Address> addresses;
}
