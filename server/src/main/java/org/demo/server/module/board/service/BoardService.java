package org.demo.server.module.board.service;

import org.demo.server.module.board.domain.Board;
import org.demo.server.module.board.request.BoardRequest;

import java.util.List;

public interface BoardService {

    // 전체 글 목록 조회
    List<Board> findAll();

    // boardId 로 글 조회
    Board findById(Long boardId);

    // 글 등록하기
    void save(BoardRequest boardRequest);

    // 글 수정하기
    void update(Long boardId, BoardRequest boardRequest);

    // 글 삭제하기
    void deleteById(Long boardId);
}
