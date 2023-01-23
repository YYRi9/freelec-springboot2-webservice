package com.jojoldu.book.springboot_practice2.service.posts;

import com.jojoldu.book.springboot_practice2.domain.posts.Posts;
import com.jojoldu.book.springboot_practice2.domain.posts.PostsRepository;
import com.jojoldu.book.springboot_practice2.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot_practice2.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot_practice2.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor    // final이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성해줌. 의존성 관계가 변경될 때마다 코드를 계속해서 수정하는 번거로움을 해결
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
