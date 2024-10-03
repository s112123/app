package org.demo.server.module.board.service;

import lombok.RequiredArgsConstructor;
import org.demo.server.module.board.domain.Board;
import org.demo.server.module.board.repository.BoardRepository;
import org.demo.server.module.board.request.BoardRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).get();
    }

    @Override
    public void save(BoardRequest boardRequest) {
        Board board = new Board(boardRequest.getWriter(), boardRequest.getTitle(), boardRequest.getContent());
        boardRepository.save(board);
    }

    @Override
    public void update(Long boardId, BoardRequest boardRequest) {
        Board board = new Board(boardRequest.getTitle(), boardRequest.getContent());
        boardRepository.update(boardId, board);
    }

    @Override
    public void deleteById(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
