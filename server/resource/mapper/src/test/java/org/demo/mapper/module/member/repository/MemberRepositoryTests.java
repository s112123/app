package org.demo.mapper.module.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Save Member When Valid Data Then Success")
    void saveMemberWhenValidDataThenSuccess() {
        // given
        String email = "a1234@email.com";
        String username = "a1234";
        String password = "1234";
        Member expected = Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();

        // when
        Member actual = memberRepository.save(expected);

        // then
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    @DisplayName("Update Role Then Success")
    void updateRoleThenSuccess() {
        // given
        String email = "a1234@email.com";
        String username = "a1234";
        String password = "1234";
        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
        memberRepository.save(member);

        // when
        memberRepository.updateRole(member.getId(), Role.ADMIN);

        // then

    }

    @Test
    @DisplayName("Find Member Roles")
    void findMemberRoles() {
        // given
        String email = "a1234@email.com";
        String username = "a1234";
        String password = "1234";
        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
        Member saveMember = memberRepository.save(member);
        memberRepository.updateRole(saveMember.getId(), Role.ADMIN);

        // when
        Set<Role> memberRoles = memberRepository.findMemberRoles(saveMember.getId());
        log.info("memberRoles={}", memberRoles);
    }
}