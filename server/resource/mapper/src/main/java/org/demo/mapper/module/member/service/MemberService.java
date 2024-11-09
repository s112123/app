package org.demo.mapper.module.member.service;

import org.demo.mapper.module.member.response.MemberDetails;
import org.demo.mapper.module.member.request.MemberRequest;

public interface MemberService {

    // 회원 등록
    MemberDetails save(MemberRequest request);

    // 회원 조회
    MemberDetails findById(Long memberId);
}
