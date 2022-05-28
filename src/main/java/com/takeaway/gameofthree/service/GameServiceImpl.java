package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.MoveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final PlayerService playerService;
    private MoveDTO firstMove;

    @Override
    public void firstMove(MoveDTO move) {
        this.firstMove = move;
    }

    @Override
    public Optional<MoveDTO> getFirstMove() {
        return Optional
                .ofNullable(firstMove);
    }

    @Override
    public MoveDTO playMove(MoveDTO move) {
        firstMove = null;
        int curr = move.getCurr();
        String playerId = playerService.getRivalPlayer(
                move.getPlayerId())
                .orElseThrow(() -> new IllegalStateException("second player not found!!")
                );
        if(curr % 3 == 0){
            return new MoveDTO(curr, curr/3, ADD_ZERO, playerId);
        } else if((curr + 1) % 3 == 0) {
            return new MoveDTO(curr, (curr + 1)/3, ADD_ONE, playerId);
        } else {
            return new MoveDTO(curr, (curr - 1)/3, SUBTRACT_ONE, playerId);
        }
    }
}
