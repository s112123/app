package org.demo.mapper.module.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.request.MemberUpdateRequest;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Member {

    private Long id;
    private int roleId;
    private String email;
    private String username;
    private String password;
    private MemberStatus status;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Member(MemberRequest request) {
        this.email = request.getEmail();
        this.username = request.getUsername();
        this.password = request.getPassword();
    }

    public Member(MemberUpdateRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
    }

    public void addRoleId(int roleId) {
        this.roleId = roleId;
    }
}
