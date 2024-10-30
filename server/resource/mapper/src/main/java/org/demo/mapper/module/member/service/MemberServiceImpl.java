package org.demo.mapper.module.member.service;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.exception.DuplicateEmailException;
import org.demo.mapper.module.member.exception.NotFoundEmailException;
import org.demo.mapper.module.member.repository.MemberRepository;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.request.MemberUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 권한 번호 조회
    @Override
    public int getRoleId(MemberRole role) {
        return memberRepository.getRoleId(role);
    }

    // 권한 조회
    @Override
    public MemberRole getRole(int roleId) {
        return memberRepository.getRole(roleId);
    }

    // 회원 등록
    @Override
    public Member save(MemberRequest memberRequest) {
        // 중복 아이디 체크
        if (memberRepository.findByEmail(memberRequest.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }
        return memberRepository.save(new Member(memberRequest));
    }

    // 회원 조회 (by Email)
    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException());
    }

    // 회원 목록
    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 회원 정보 변경
    @Override
    public void updateMember(String email, MemberUpdateRequest updateRequest) {
        memberRepository.updateMember(email, new Member(updateRequest));
    }

    // 회원 상태 변경
    @Override
    public void updateMemberStatus(String email, MemberStatus status) {
        memberRepository.updateMemberStatus(email, status);
    }

    // 회원 권한 변경
    @Override
    public void updateMemberRole(String email, MemberRole role) {
        memberRepository.updateMemberRole(email, role);
    }

    // 회원 삭제 (by Id)
    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    // 회원 삭제 (by Email)
    @Override
    public void deleteByEmail(String email) {
        memberRepository.deleteByEmail(email);
    }
}
