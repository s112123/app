package org.demo.mapper.module.member.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.domain.MemberRole;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 권한 번호 조회
    int getRoleId(MemberRole role);

    // 권한 조회
    MemberRole getRole(int roleId);

    // 회원 등록
    void save(Member member);

    // 회원 조회 (by Id)
    Member findById(Long id);

    // 회원 조회 (by Email)
    Member findByEmail(String email);

    // 회원 목록
    List<Member> findAll();

    // 회원 정보 변경
    void updateMember(@Param("email") String email, @Param("member") Member member);

    // 회원 상태 변경
    void updateMemberStatus(@Param("email") String email, @Param("status") MemberStatus status);

    // 회원 권한 변경
    void updateMemberRole(@Param("email") String email, @Param("roleId") int roleId);

    // 회원 삭제 (by Id)
    void deleteById(Long id);

    // 회원 삭제 (by Email)
    void deleteByEmail(String email);
}
