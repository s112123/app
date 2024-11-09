package org.demo.mapper.module.member.repository;

import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;

import java.util.Optional;
import java.util.Set;

public interface MemberRepository {

    // 회원 등록
    Member save(Member member);

    // 회원 조회
    Optional<Member> findById(Long memberId);

    // 회원 조회
    Optional<Member> findByEmail(String email);

    // 회원 권한 조회
    Set<Role> findMemberRoles(Long memberId);

    // 회원 권한 변경
    void updateRole(Long memberId, Role role);
}
