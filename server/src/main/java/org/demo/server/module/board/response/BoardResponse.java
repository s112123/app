package org.demo.server.module.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.demo.server.module.board.domain.Board;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardResponse {

    private Long boardId;
    private String writer;
    private String title;
    private String content;
    private Long hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
