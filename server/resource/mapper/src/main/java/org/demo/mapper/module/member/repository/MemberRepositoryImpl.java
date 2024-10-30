package org.demo.mapper.module.member.repository;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.repository.mapper.MemberMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;

    // 권한 번호 조회
    @Override
    public int getRoleId(MemberRole memberRole) {
        return memberMapper.getRoleId(memberRole);
    }

    // 권한 조회
    @Override
    public MemberRole getRole(int roleId) {
        return memberMapper.getRole(roleId);
    }

    // 회원 등록
    @Override
    public Member save(Member member) {
        // 기본 권한 설정
        member.addRoleId(getRoleId(MemberRole.USER));
        // 회원 저장
        memberMapper.save(member);
        // 회원 정보 반환
        return memberMapper.findById(member.getId());
    }

    // 회원 조회 (by Id)
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberMapper.findById(id));
    }

    // 회원 조회 (by Email)
    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(memberMapper.findByEmail(email));
    }

    // 회원 목록
    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    // 회원 정보 변경
    @Override
    public void updateMember(String email, Member member) {
        memberMapper.updateMember(email, member);
    }

    // 회원 상태 변경
    @Override
    public void updateMemberStatus(String email, MemberStatus status) {
        memberMapper.updateMemberStatus(email, status);
    }

    // 회원 권한 변경
    @Override
    public void updateMemberRole(String email, MemberRole role) {
        memberMapper.updateMemberRole(email, getRoleId(role));
    }

    // 회원 삭제 (by Id)
    @Override
    public void deleteById(Long id) {
        memberMapper.deleteById(id);
    }

    // 회원 삭제 (by Email)
    @Override
    public void deleteByEmail(String email) {
        memberMapper.deleteByEmail(email);
    }
}
