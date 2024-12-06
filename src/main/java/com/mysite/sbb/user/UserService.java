package com.mysite.sbb.user;

import com.mysite.sbb.question.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //가입
    public SiteUser create(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        //비밀번호 암호화 기능 추가
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); SecurityConfig 에서 객체 주입받아 사용하기 때문에 필요X
        siteUser.setPassword(passwordEncoder.encode(password));
        //저장하기
        userRepo.save(siteUser);
        return siteUser;
    }

    //유저네임(id)으로 유지 객체를 조회
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepo.findByusername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}
