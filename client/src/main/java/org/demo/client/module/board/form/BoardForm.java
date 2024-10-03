package org.demo.client.module.board.form;

import lombok.Data;

@Data
public class BoardForm {

    private Long boardId;
    private String writer;
    private String title;
    private String content;
}
