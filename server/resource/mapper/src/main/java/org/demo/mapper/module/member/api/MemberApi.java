package org.demo.mapper.module.member.api;

import lombok.RequiredArgsConstructor;
import org.demo.mapper.module.member.exception.DuplicatedEmailException;
import org.demo.mapper.module.member.exception.NotFoundMemberException;
import org.demo.mapper.module.member.response.ErrorResponse;
import org.demo.mapper.module.member.response.MemberDetails;
import org.demo.mapper.module.member.request.MemberRequest;
import org.demo.mapper.module.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    // 등록하기
    @PostMapping
    public ResponseEntity<MemberDetails> save(@RequestBody MemberRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.save(request));
    }

    // 조회하기
    @GetMapping("/{id}")
    public ResponseEntity<MemberDetails> findById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findById(id));
    }

    @ExceptionHandler
    public ResponseEntity<?> duplicatedEmail(DuplicatedEmailException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> notFoundMember(NotFoundMemberException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
