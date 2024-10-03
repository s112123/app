package org.demo.server.module.board.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {

    private String writer;
    private String title;
    private String content;
}
