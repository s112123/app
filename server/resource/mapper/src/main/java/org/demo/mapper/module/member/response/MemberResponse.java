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

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.status = member.getStatus();
        this.regDate = member.getRegDate();
        this.modDate = member.getModDate();
    }

    public void addRole(MemberRole role) {
        this.role = role;
    }
}
