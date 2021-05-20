package com.chenkesi.securityframework.domain.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GenericGenerator(name = "idGenerator",strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id",length = 50)
    private String id;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "username", length = 64)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "密码不能为空")
    private String password;

}
