package com.jojoldu.book.springboot_practice2.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // order by는 정렬. desc는 내림차순 정렬. 여기서 p.id는 정렬하고자 하는 컬럼 이름
    List<Posts> findAllDesc();
}
