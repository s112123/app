package org.demo.server.module.board.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo.server.module.board.domain.Board;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    
    // 전체 글 목록 조회
    List<Board> findAll();

    // board_id 로 글 조회
    Board findById(Long boardId);

    // 글 등록하기
    void save(Board board);

    // 글 수정하기
    void update(@Param("boardId") Long boardId, @Param("board") Board board);

    // 글 삭제하기
    void deleteById(Long boardId);
}
