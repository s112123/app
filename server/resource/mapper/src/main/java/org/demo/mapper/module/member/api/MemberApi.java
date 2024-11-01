package org.demo.mapper.module.member.api;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.member.domain.Member;
import org.demo.mapper.module.member.domain.MemberRole;
import org.demo.mapper.module.member.domain.MemberStatus;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.request.MemberUpdateRequest;
import org.demo.mapper.module.member.response.MemberResponse;
import org.demo.mapper.module.member.response.MembersResponse;
import org.demo.mapper.module.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    // 등록하기
    @PostMapping
    public ResponseEntity<MemberResponse> save(@RequestBody MemberRequest memberRequest) {
        Member saveMember = memberService.save(memberRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createMemberResponse(saveMember));
    }

    // 조회하기 (by Email)
    @GetMapping("/{email}")
    public ResponseEntity<MemberResponse> findById(@PathVariable("email") String email) {
        Member findMember = memberService.findByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createMemberResponse(findMember));
    }

    // 목록조회
    @GetMapping
    public ResponseEntity<MembersResponse> findAll() {
        List<Member> findMembers = memberService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MembersResponse(findMembers));
    }

    // 회원 정보 변경
    @PatchMapping("/{email}")
    public ResponseEntity<Void> updateMember(
            @PathVariable("email") String email,
            @RequestBody MemberUpdateRequest updateRequest
    ) {
        memberService.updateMember(email, updateRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // 회원 상태 변경
    @PatchMapping("/{email}/status/{status}")
    public ResponseEntity<Void> updateMemberStatus(
            @PathVariable("email") String email,
            @PathVariable("status") MemberStatus status
    ) {
        memberService.updateMemberStatus(email, status);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // 회원 권한 변경
    @PatchMapping("/{email}/role/{role}")
    public ResponseEntity<Void> updateMemberRole(
            @PathVariable("email") String email,
            @PathVariable("role") MemberRole role
    ) {
        memberService.updateMemberRole(email, role);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // 회원 삭제 (by Email)
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteMemberByEmail(@PathVariable("email") String email) {
        memberService.deleteByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    // MemberResponse 생성
    private MemberResponse createMemberResponse(Member member) {
        MemberResponse memberResponse = new MemberResponse(member);
        memberResponse.addRole(memberService.getRole(member.getRoleId()));
        return memberResponse;
    }
}
