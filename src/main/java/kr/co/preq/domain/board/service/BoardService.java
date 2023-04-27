package kr.co.preq.domain.board.service;

import kr.co.preq.domain.board.entity.Board;
import kr.co.preq.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public void write(Board board) {
        boardRepository.save(board);
    }
}
