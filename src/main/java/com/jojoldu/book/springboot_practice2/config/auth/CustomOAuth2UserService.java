package com.jojoldu.book.springboot_practice2.config.auth;

import com.jojoldu.book.springboot_practice2.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot_practice2.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot_practice2.domain.users.Users;
import com.jojoldu.book.springboot_practice2.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UsersRepository usersRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registartionId = userRequest.getClientRegistration().getRegistrationId();    // 현재 로그인 진행 중인 서비스를 구분하는 코드. 구글만 사용시에는 불필요. 이후 다른 소셜로그인 연동 시 구분하기위해 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();   // 프라이머리키와 같음. 구글의 경우에만 기본 코드 지원. 네이버 로그인 동시 지원 시 사용

        OAuthAttributes attributes = OAuthAttributes.of(registartionId, userNameAttributeName, oAuth2User.getAttributes()); //
        Users users = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(users));   // 사용자 정보를 저장하기 위한 Dto 클래스.

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(users.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Users saveOrUpdate(OAuthAttributes attributes) {
        Users users = usersRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return usersRepository.save(users);
    }
}
