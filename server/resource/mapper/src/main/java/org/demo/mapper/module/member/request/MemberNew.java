package org.demo.mapper.module.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberNew {

    private String email;
    private String username;
    private String password;
}
