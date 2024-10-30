package org.demo.mapper.module.member.repository;

import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 권한 번호 조회
    int getRoleId(MemberRole role);

    // 권한 조회
    MemberRole getRole(int roleId);

    // 회원 등록
    Member save(Member member);

    // 회원 조회 (by Id)
    Optional<Member> findById(Long id);

    // 회원 조회 (by Email)
    Optional<Member> findByEmail(String email);

    // 회원 목록
    List<Member> findAll();

    // 회원 정보 변경
    void updateMember(String email, Member member);

    // 회원 상태 변경
    void updateMemberStatus(String email, MemberStatus status);

    // 회원 권한 변경
    void updateMemberRole(String email, MemberRole role);

    // 회원 삭제 (by Id)
    void deleteById(Long id);

    // 회원 삭제 (by Email)
    void deleteByEmail(String email);
}
