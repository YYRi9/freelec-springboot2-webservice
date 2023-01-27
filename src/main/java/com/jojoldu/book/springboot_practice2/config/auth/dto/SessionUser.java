package com.jojoldu.book.springboot_practice2.config.auth.dto;

import com.jojoldu.book.springboot_practice2.domain.users.Users;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

// 인증된 사용자 정보만 필요로 하기 때문에 필요한 것만 필드로 선언