package com.jojoldu.book.springboot_practice2.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);  // 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드
}
