package org.demo.mapper.module.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.demo.mapper.module.file.response.FileInformation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberRequest {

    private String email;
    private String username;
    private String password;
    private FileInformation fileInformation;
}
