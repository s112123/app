package org.demo.mapper.module.member.service;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.member.details.MemberDetails;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;
import org.demo.mapper.module.member.exception.NotFoundMemberException;
import org.demo.mapper.module.member.repository.MemberRepository;
import org.demo.mapper.module.member.request.MemberRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 회원 등록
    @Override
    public MemberDetails save(MemberRequest request) {
        // 회원 저장
        Member saveMember = memberRepository.save(Member.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .build());
        // 권한 조회
        Set<Role> memberRoles = memberRepository.findMemberRoles(saveMember.getId());
        return new MemberDetails(saveMember, memberRoles);
    }

    // 회원 조회
    @Override
    public MemberDetails findById(Long memberId) {
        // 회원 조회
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원"));
        // 권한 조회
        Set<Role> memberRoles = memberRepository.findMemberRoles(findMember.getId());
        return new MemberDetails(findMember, memberRoles);
    }
}
