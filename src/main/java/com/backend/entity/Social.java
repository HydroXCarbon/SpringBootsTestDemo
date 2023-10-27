package com.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name="m_social")
public class Social extends BaseEntity {

    @Column(name="Facebook",length = 60)
    private String facebook;

    @Column(name="Line",length = 120)
    private String line;

    @Column(name="Instagram", length = 120)
    private String instagram;

    @Column(name="Tiktok", length = 120)
    private String tiktok;

    @OneToOne
    @JoinColumn(name = "m_user_id", nullable = false)
    private User user;

}
