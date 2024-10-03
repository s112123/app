package org.demo.server.module.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.server.module.board.domain.Board;
import org.demo.server.module.board.request.BoardRequest;
import org.demo.server.module.board.response.BoardResponse;
import org.demo.server.module.board.response.BoardListResponse;
import org.demo.server.module.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 전체 글 목록 조회
    @GetMapping
    public BoardListResponse boardList() {
        List<Board> boards = boardService.findAll();
        BoardListResponse boardListResponse = new BoardListResponse(boards.size(), boards);
        return boardListResponse;
    }

    // boardId 로 글 조회
    @GetMapping("/{boardId}")
    public BoardResponse boardById(@PathVariable("boardId") Long boardId) {
        Board board = boardService.findById(boardId);
        BoardResponse boardResponse = new BoardResponse(
                board.getBoardId(),
                board.getWriter(),
                board.getTitle(),
                board.getContent(),
                board.getHits(),
                board.getRegDate(),
                board.getModDate()
        );
        return boardResponse;
    }

    // 글 등록하기
    @PostMapping
    public String saveBoard(@RequestBody BoardRequest boardRequest) {
        boardService.save(boardRequest);
        return "ok";
    }

    // 글 수정하기
    @PatchMapping("/{boardId}")
    public String updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardRequest boardRequest) {
        boardService.update(boardId, boardRequest);
        return "ok";
    }

    // 글 삭제하기
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteById(boardId);
        return "ok";
    }
}
