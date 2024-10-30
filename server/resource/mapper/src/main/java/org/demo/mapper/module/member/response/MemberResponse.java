package org.demo.mapper.module.member.response;

import lombok.Getter;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private Long id;
    private String email;
    private String username;
    private String password;
    private MemberStatus status;
    private MemberRole role;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public MemberResponse(Member saveMember) {
        this.id = saveMember.getId();
        this.email = saveMember.getEmail();
        this.username = saveMember.getUsername();
        this.password = saveMember.getPassword();
        this.status = saveMember.getStatus();
        this.regDate = saveMember.getRegDate();
        this.modDate = saveMember.getModDate();
    }

    public void addRole(MemberRole role) {
        this.role = role;
    }
}
