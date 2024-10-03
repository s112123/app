package org.demo.server.module.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class Board {

    private Long boardId;
    private String writer;
    private String title;
    private String content;
    private Long hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
