package org.demo.server.module.board.repository;

import lombok.RequiredArgsConstructor;
import org.demo.server.module.board.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardMapper boardMapper;

    @Override
    public List<Board> findAll() {
        return boardMapper.findAll();
    }

    @Override
    public Optional<Board> findById(Long boardId) {
        return Optional.ofNullable(boardMapper.findById(boardId));
    }

    @Override
    public void save(Board board) {
        boardMapper.save(board);
    }

    @Override
    public void update(Long boardId, Board board) {
        boardMapper.update(boardId, board);
    }

    @Override
    public void deleteById(Long boardId) {
        boardMapper.deleteById(boardId);
    }
}
