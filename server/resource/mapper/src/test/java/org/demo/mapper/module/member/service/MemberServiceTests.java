package org.demo.mapper.module.member.service;

import org.demo.mapper.module.member.details.MemberDetails;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;
import org.demo.mapper.module.member.repository.MemberRepository;
import org.demo.mapper.module.member.request.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberServiceTests {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("Save Member When Valid Data Then Success")
    void saveMemberWhenValidDataThenSuccess() {
        // given
        String email = "a1234@email.com";
        String username = "a1234";
        String password = "1234";
        MemberRequest expected = new MemberRequest(email, username, password);

        // stub
        Member findMember = Member.builder()
                .id(1L)
                .email(expected.getEmail())
                .username(expected.getUsername())
                .password(expected.getPassword())
                .build();
        when(memberRepository.save(any(Member.class))).thenReturn(findMember);
        when(memberRepository.findMemberRoles(findMember.getId())).thenReturn(Set.of(Role.USER));

        // when
        MemberDetails actual = memberService.save(expected);

        // then
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getRoles()).containsOnly(Role.USER);

        // verify
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(memberRepository, times(1)).findMemberRoles(findMember.getId());
    }

    @Test
    @DisplayName("Find Member When Valid Id Then Success")
    void findMemberWhenValidIdThenSuccess() {
        // given
        Long id = 1L;
        String email = "a1234@email.com";
        String username = "a1234";
        String password = "1234";
        Set<Role> roles = Set.of(Role.USER);
        LocalDateTime regDate = LocalDateTime.now();

        // stub
        Member findMember = Member.builder()
                .id(id)
                .email(email)
                .username(username)
                .password(password)
                .regDate(regDate)
                .build();
        when(memberRepository.findById(id)).thenReturn(Optional.of(findMember));
        when(memberRepository.findMemberRoles(id)).thenReturn(roles);

        // when
        MemberDetails actual = memberService.findById(id);

        // then
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getRoles()).contains(Role.USER);

        // verify
        verify(memberRepository, times(1)).findById(id);
        verify(memberRepository, times(1)).findMemberRoles(id);
    }
}