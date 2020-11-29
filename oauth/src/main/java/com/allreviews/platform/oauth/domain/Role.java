package com.allreviews.platform.oauth.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLD_ID")
    private Integer roleId;

    @Column(name = "ROLE")
    private String role;
}
