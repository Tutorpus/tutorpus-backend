package com.tutorpus.tutorpus.auth;

import com.tutorpus.tutorpus.auth.dto.OAuthAttributes;
import com.tutorpus.tutorpus.auth.dto.SessionUser;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

//로그인 이후 가져온 사용자의 정보(email, name)들을 기반으로 가입 및 정보 수정, 세션 저장 등의 기능 지원
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 지금은 구글만 사용하기 때문에 불필요하지만, 이후 네이버 로그인 연동 시에 네이버인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가 되는 필드값. Primary Key와 같은 의미.
        String userNameAttributionName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributionName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SessionUser(member));    //세션에 사용자 정보 저장

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    //저장 및 구글 사용자 정보가 업데이트 되었을 때를 대비. 사용자의 이름 변경되면 User 엔티티에도 반영.
    private Member saveOrUpdate(OAuthAttributes attributes) {
        //세션에 저장해놓은 role 가져오기
        Role role = (Role) httpSession.getAttribute("preRole");
        Member member = userRepository.findByEmail(attributes.getEmail())
                // 구글 사용자 정보 업데이트(이미 가입된 사용자) => 업데이트
                .map(entity -> entity.update(attributes.getName()))
                // 가입되지 않은 사용자 => User 엔티티 생성(session에서 가져온 role)
                .orElse(attributes.toEntity(role));

        return userRepository.save(member);
    }
}

