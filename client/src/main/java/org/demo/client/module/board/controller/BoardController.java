package org.demo.client.module.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.client.module.board.form.BoardForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final RestTemplate restTemplate;

    // 전체 글 목록 페이지
    @GetMapping("/list")
    public String boardListGET(Model model) {
        Object response = restTemplate.getForObject("http://localhost:8081/boards", Object.class);
        model.addAttribute("response", response);
        return "board/list";
    }

    // 글 조회 페이지, 글 수정 페이지
    @GetMapping({"/view", "/edit"})
    public void boardGET(@RequestParam("board_id") Long boardId, Model model) {
        Object response = restTemplate.getForObject("http://localhost:8081/boards/{boardId}", Object.class, boardId);
        model.addAttribute("response", response);
    }

    // 글 등록 페이지
    @GetMapping("/write")
    public String saveBoardGET() {
        return "board/write";
    }

    // 글 등록하기
    @PostMapping("/write")
    public String saveBoardPOST(BoardForm boardForm) {
        String response = restTemplate.postForObject("http://localhost:8081/boards", boardForm, String.class);
        return "redirect:/board/list";
    }

    // 글 수정하기
    @PostMapping("/edit")
    public String editBoard(BoardForm boardForm, RedirectAttributes redirectAttributes) {
        String response = restTemplate.patchForObject(
                "http://localhost:8081/boards/{boardId}",
                boardForm,
                String.class,
                boardForm.getBoardId()
        );
        redirectAttributes.addAttribute("board_id", boardForm.getBoardId());
        return "redirect:/board/view";
    }

    // 글 삭제하기
    @GetMapping("/delete")
    public String deleteBoard(@RequestParam("board_id") Long boardId) {
        restTemplate.delete("http://localhost:8081/boards/{boardId}", boardId);
        return "redirect:/board/list";
    }
}
