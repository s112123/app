package org.demo.mapper.module.member.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;

import java.util.Set;

@Mapper
public interface MemberMapper {

    // 회원 등록
    void save(Member member);

    // 회원 조회
    Member findById(Long memberId);

    // 회원 권한 조회
    Set<Role> findMemberRoles(Long memberId);
}
