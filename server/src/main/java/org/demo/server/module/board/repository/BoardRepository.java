package org.demo.server.module.board.repository;

import org.demo.server.module.board.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    // 전체 글 목록 조회
    List<Board> findAll();

    // boardId 로 글 조회
    Optional<Board> findById(Long boardId);

    // 글 등록하기
    void save(Board board);

    // 글 수정하기
    void update(Long boardId, Board board);

    // 글 삭제하기
    void deleteById(Long boardId);
}
