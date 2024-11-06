package org.demo.mapper.module.member.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo.mapper.module.member.domain.Role;

@Mapper
public interface RoleMapper {

    // 회원 권한 등록
    void save(@Param("memberId") Long memberId, @Param("roleId") int roleId);

    // 권한 번호 조회
    int findRoleId(Role role);

    // 권한 이름 조회
    Role findRole(int roleId);

    // 회원 권한 삭제
    void deleteAll(Long memberId);
}
