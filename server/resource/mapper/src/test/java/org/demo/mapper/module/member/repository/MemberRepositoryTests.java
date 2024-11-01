package org.demo.mapper.module.member.repository;

import org.demo.mapper.module.file.response.FileInformation;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.request.MemberUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Save Member")
    void saveMember() {
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest("email@email.com", "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        Member saveMember = memberRepository.save(member);

        assertThat(saveMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(saveMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(saveMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(saveMember.getStatus()).isEqualTo(MemberStatus.OFFLINE);
        assertThat(saveMember.getRegDate()).isNotNull();
        assertThat(saveMember.getModDate()).isNotNull();
    }

    @Test
    @DisplayName("Find Member By Id")
    void findMemberById() {
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest("email@email.com", "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).orElseThrow();

        assertThat(findMember.getEmail()).isEqualTo(saveMember.getEmail());
        assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
        assertThat(findMember.getPassword()).isEqualTo(saveMember.getPassword());
        assertThat(findMember.getStatus()).isEqualTo(saveMember.getStatus());
        assertThat(findMember.getRegDate()).isEqualTo(saveMember.getRegDate());
        assertThat(findMember.getModDate()).isEqualTo(saveMember.getModDate());
    }

    @Test
    @DisplayName("Find Member By Email")
    void findMemberByEmail() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        assertThat(findMember.getEmail()).isEqualTo(saveMember.getEmail());
        assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
        assertThat(findMember.getPassword()).isEqualTo(saveMember.getPassword());
        assertThat(findMember.getStatus()).isEqualTo(saveMember.getStatus());
        assertThat(findMember.getRegDate()).isEqualTo(saveMember.getRegDate());
        assertThat(findMember.getModDate()).isEqualTo(saveMember.getModDate());
    }

    @Test
    @DisplayName("Find Member By No Email")
    void findMemberByNoEmail() {
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest("email@email.com", "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        memberRepository.save(member);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> memberRepository.findByEmail("b1234@demo.com").orElseThrow());
    }

    @Test
    @DisplayName("Find Members")
    void findMembers() {
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        for (int i = 1; i <= 255; i++) {
            MemberRequest memberRequest =
                    new MemberRequest("email" + i + "@email.com", "username" + i, "password", fileInformation);
            Member member = new Member(memberRequest);
            memberRepository.save(member);
        }
        List<Member> findMembers = memberRepository.findAll();

        assertThat(findMembers.size()).isEqualTo(255);
    }

    @Test
    @DisplayName("Update Member Information")
    void updateMemberInformation() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        memberRepository.save(member);
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest("newUsername", "newPassword");
        Member updateMember = new Member(memberUpdateRequest);
        memberRepository.updateMember(email, updateMember);
        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        assertThat(findMember.getUsername()).isEqualTo("newUsername");
        assertThat(findMember.getPassword()).isEqualTo("newPassword");
    }

    @Test
    @DisplayName("Update Member Status")
    void updateMemberStatus() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        memberRepository.save(member);
        memberRepository.updateMemberStatus(email, MemberStatus.ONLINE);
        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        assertThat(findMember.getStatus()).isEqualTo(MemberStatus.ONLINE);
    }

    @Test
    @DisplayName("Update Member Role")
    void updateMemberRole() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        memberRepository.save(member);
        memberRepository.updateMemberRole(email, MemberRole.ADMIN);
        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        assertThat(findMember.getRoleId()).isEqualTo(2);
    }

    @Test
    @DisplayName("Delete Member By Id")
    void deleteMemberById() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        Member saveMember = memberRepository.save(member);
        memberRepository.deleteById(saveMember.getId());

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> memberRepository.findById(saveMember.getId()).orElseThrow());
    }

    @Test
    @DisplayName("Delete Member By Email")
    void deleteMemberByEmail() {
        String email = "email@email.com";
        FileInformation fileInformation =
                new FileInformation("filePath", "originalFileName", "uploadFileName", "extension");
        MemberRequest memberRequest = new MemberRequest(email, "username", "password", fileInformation);
        Member member = new Member(memberRequest);

        Member saveMember = memberRepository.save(member);
        memberRepository.deleteByEmail(saveMember.getEmail());

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> memberRepository.findByEmail(saveMember.getEmail()).orElseThrow());
    }
}