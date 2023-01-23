package com.jojoldu.book.springboot_practice2.domain.posts;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // 이걸 사용하면 H2 데이터베이스를 자동으로 실행해 준다.
public class PostsRepositoryTest {  // save, findAll 기능을 테스트한다.

    @Autowired
    PostsRepository postsRepository;

    @After  // 단위 테스트가 끝날 때마다 수행되는 메소드를 지정한다.
    public void cleanup() {
        postsRepository.deleteAll();
    }
    
    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String  content = "테스트 본문";
        
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());
        
        // when
        List<Posts> postsList = postsRepository.findAll();  // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드 (findAll)
        
        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
       // given
        LocalDateTime now =  LocalDateTime.of(2023, 1, 23, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>> createDate=" + posts.getCreateDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
