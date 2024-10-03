package org.demo.server.module.board.response;

import lombok.*;
import org.demo.server.module.board.domain.Board;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardListResponse {

    private long size;
    private List<Board> data;
}
