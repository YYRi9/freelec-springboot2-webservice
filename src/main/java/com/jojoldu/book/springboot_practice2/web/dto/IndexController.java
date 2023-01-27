package com.jojoldu.book.springboot_practice2.web.dto;

import com.jojoldu.book.springboot_practice2.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot_practice2.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");  // 로그인 성공 시 세션에 SessionUser 를 저장하도록 함
        if(user != null) {  // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다. 만약 저장된 값이 없으면 model엔 아무런 값이 없는 상태로, 로그인 버튼이 보이게 된다.
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
