package org.demo.mapper.module.member.repository;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;
import org.demo.mapper.module.member.repository.mapper.MemberMapper;
import org.demo.mapper.module.member.repository.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;
    private final RoleMapper roleMapper;

    // 회원 등록
    @Override
    public Member save(Member member) {
        // 회원 저장
        memberMapper.save(member);
        // 회원 권한 저장 (기본 권한 → USER)
        roleMapper.save(member.getId(), roleMapper.findRoleId(Role.USER));
        // 등록된 회원 조회
        return memberMapper.findById(member.getId());
    }

    // 회원 조회
    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(memberMapper.findById(memberId));
    }

    // 회원 권한 조회
    @Override
    public Set<Role> findMemberRoles(Long memberId) {
        return memberMapper.findMemberRoles(memberId);
    }

    // 회원 권한 변경
    @Override
    public void updateRole(Long memberId, Role role) {
        // 이미 등록된 권한은 모두 삭제
        roleMapper.deleteAll(memberId);
        // 권한 변경 → 상위 권한부터 break 문 없이 등록
        switch (role) {
            case ROOT:
                roleMapper.save(memberId, roleMapper.findRoleId(Role.ROOT));
            case ADMIN:
                roleMapper.save(memberId, roleMapper.findRoleId(Role.ADMIN));
            case USER:
                roleMapper.save(memberId, roleMapper.findRoleId(Role.USER));
        }
    }
}
