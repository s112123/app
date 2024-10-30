package org.demo.mapper.module.member.response;

import lombok.Getter;
import org.demo.mapper.module.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MembersResponse {

    private long count;
    private List<MemberResponse> members;

    public MembersResponse(List<Member> members) {
        this.count = members.size();
        this.members = members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }
}
