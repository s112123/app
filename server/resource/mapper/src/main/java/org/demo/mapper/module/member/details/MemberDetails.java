package org.demo.mapper.module.member.details;

import lombok.Getter;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.Role;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class MemberDetails {

    private Long id;
    private String email;
    private String username;
    private String password;
    private Set<Role> roles;
    private LocalDateTime regDate;

    public MemberDetails(Member member, Set<Role> roles) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.regDate = member.getRegDate();
        this.roles = roles;
    }
}
