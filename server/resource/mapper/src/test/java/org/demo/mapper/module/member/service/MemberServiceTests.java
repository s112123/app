package org.demo.mapper.module.member.service;

import org.demo.mapper.module.file.mapper.FileMapper;
import org.demo.mapper.module.file.response.FileInformation;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.exception.DuplicatedEmailException;
import org.demo.mapper.module.member.exception.NotFoundEmailException;
import org.demo.mapper.module.member.repository.MemberRepository;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.request.MemberUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTests {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private FileMapper fileMapper;
    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("Get RoleId")
    void getRoleId() {
        MemberRole role = MemberRole.ROOT;

        when(memberRepository.getRoleId(role)).thenReturn(1);

        int roleId = memberService.getRoleId(role);
        assertThat(roleId).isEqualTo(1);

        verify(memberRepository, times(1)).getRoleId(role);
    }

    @Test
    @DisplayName("Save Member With No Duplicated Email")
    void saveMemberWithNoDuplicatedEmail() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            // Setter 가 없는 경우
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        });
        doNothing().when(fileMapper).saveProfileImage(any(Long.class), any(FileInformation.class));
        memberService.save(memberRequest);

        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(fileMapper, times(1)).saveProfileImage(any(Long.class), any(FileInformation.class));
    }

    @Test
    @DisplayName("Save Member With Duplicated Email")
    void saveMemberWithDuplicatedEmail() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(new Member(memberRequest)));

        assertThatExceptionOfType(DuplicatedEmailException.class)
                .isThrownBy(() -> memberService.save(memberRequest));

        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("Find Member By Exists Email")
    void findMemberByExistsEmail() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(new Member(memberRequest)));
        Member findMember = memberService.findByEmail(email);

        assertThat(findMember.getEmail()).isEqualTo(memberRequest.getEmail());
        assertThat(findMember.getUsername()).isEqualTo(memberRequest.getUsername());
        assertThat(findMember.getPassword()).isEqualTo(memberRequest.getPassword());

        verify(memberRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Find Member By Not Exists Email")
    void findMemberByNotExistsEmail() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundEmailException.class)
                .isThrownBy(() -> memberService.findByEmail(email));

        verify(memberRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Find Members")
    void findMembers() {
        List<Member> members = new ArrayList<>();
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        for (int i = 1; i <= 255; i++) {
            MemberRequest memberRequest =
                    new MemberRequest("email" + i + "@email.com", "username" + i, "password", fileInformation);
            members.add(new Member(memberRequest));
        }

        when(memberRepository.findAll()).thenReturn(members);
        List<Member> findMembers = memberService.findAll();

        assertThat(findMembers.size()).isEqualTo(255);

        verify(memberRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update Member Information")
    void updateMemberInformation() {
        // 수정된 결과의 기대값으로 만약, MemberRequest 에 다른 값을 넣으면 검증에서 실패한다
        String email = "email@email.com";
        String newUsername = "newUsername";
        String newPassword = "newPassword";
        MemberUpdateRequest updateRequest = new MemberUpdateRequest(newUsername, newPassword);

        // ArgumentCaptor 를 사용하면 전달된 Member 객체가 업데이트 되었는지 검증할 수 있다
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        memberService.updateMember(email, updateRequest);

        // capture() 를 사용하면 Mockito 가 해당 인자를 저장한다
        verify(memberRepository, times(1)).updateMember(any(String.class), memberCaptor.capture());

        // memberCaptor.getValue() 는 verify() 호출 이후에 사용할 수 있다
        Member capturedMember = memberCaptor.getValue();
        assertThat(capturedMember.getUsername()).isEqualTo(newUsername);
        assertThat(capturedMember.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("Update Member Status")
    void updateMemberStatus() {
        String email = "email@email.com";
        MemberStatus status = MemberStatus.ONLINE;

        doNothing().when(memberRepository).updateMemberStatus(email, status);
        memberService.updateMemberStatus(email, status);

        verify(memberRepository, times(1)).updateMemberStatus(email, status);
    }

    @Test
    @DisplayName("Update Member Role")
    void updateMemberRole() {
        String email = "email@email.com";
        MemberRole role = MemberRole.ADMIN;

        doNothing().when(memberRepository).updateMemberRole(email, role);
        memberService.updateMemberRole(email, role);

        verify(memberRepository, times(1)).updateMemberRole(email, role);
    }

    @Test
    @DisplayName("Delete Member By Id")
    void deleteMemberById() {
        Long id = 1L;

        doNothing().when(memberRepository).deleteById(id);
        memberService.deleteById(id);

        verify(memberRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Delete Member By Email")
    void deleteMemberByEmail() {
        String email = "email@email.com";
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(memberRepository).deleteByEmail(email);
        memberService.deleteByEmail(email);

        verify(memberRepository, times(1)).deleteByEmail(emailCaptor.capture());

        String capturedEmail = emailCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(email);
    }
}