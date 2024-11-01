package org.demo.client.module.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    // 회원 등록 페이지
    @GetMapping("/add")
    public String viewAdd() {
        return "member/add";
    }

    // 회원 목록 페이지
    @GetMapping("/list")
    public String viewMembers() {
        return "member/list";
    }

    // 회원 조회 페이지
    @GetMapping("/view/{email}")
    public String viewMember() {
        return "member/view";
    }

    // 회원 정보 변경 페이지
    @GetMapping("/edit")
    public String viewEdit() {
        return "member/edit";
    }
}
