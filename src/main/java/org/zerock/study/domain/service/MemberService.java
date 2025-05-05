package org.zerock.study.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.study.domain.DTO.SigninRequest;
import org.zerock.study.domain.DTO.SignupRequest;
import org.zerock.study.domain.entity.Members;
import org.zerock.study.domain.repository.MemberRepo;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepo memberRepo;
    //
    public void signup(SignupRequest signupRequest) {
        if(memberRepo.existsByEmail(signupRequest.email())){
            throw new IllegalArgumentException("Email already in use");
        }
        Members members = Members.builder()
                .email(signupRequest.email())
                .password(signupRequest.password())
                .name(signupRequest.name())
                .build();

        memberRepo.save(members);
    }

    public Members signin(SigninRequest signinRequest) {
        boolean isEmailExist = memberRepo.existsByEmail(signinRequest.email());
        if(!isEmailExist){
            throw new IllegalArgumentException("Email not exist");
        }

        Members members = memberRepo.findMembersByEmailAndPassword(signinRequest.email(), signinRequest.password());

        if(members == null){
            throw new IllegalArgumentException("Member not exist");
        }

        return members;
    }

    // 회원 탈퇴
    public void unsubscript(String email) {
        Members members = memberRepo.findMembersByEmail(email);
        if(members == null){
            throw new IllegalArgumentException("Email not exist");
        }
        memberRepo.delete(members);
    }

}
