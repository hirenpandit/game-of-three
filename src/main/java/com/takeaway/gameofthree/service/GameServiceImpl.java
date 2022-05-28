package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.MoveDTO;
import com.takeaway.gameofthree.events.WinLoseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final PlayerService playerService;

    private final ApplicationEventPublisher publisher;
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
        String gameId = move.getGameId();
        Optional<String> optionalId = playerService.getRivalPlayer(
                gameId,
                move.getPlayerId());
        if(optionalId.isPresent()) {
            firstMove = null;
            MoveDTO moveDTO = null;
            int curr = move.getCurr();
            if(curr % 3 == 0){
                moveDTO = new MoveDTO(curr, curr / 3, ADD_ZERO, optionalId.get(), gameId);
            } else if((curr + 1) % 3 == 0) {
                moveDTO =  new MoveDTO(curr, (curr + 1)/3, ADD_ONE, optionalId.get(), gameId);
            } else {
                moveDTO = new MoveDTO(curr, (curr - 1)/3, SUBTRACT_ONE, optionalId.get(), gameId);
            }
            if(moveDTO.getNext() == 0){
                publisher.publishEvent(new WinLoseEvent(true, moveDTO.getPlayerId()));
                publisher.publishEvent(new WinLoseEvent(false, move.getPlayerId()));
            }
            return moveDTO;
        } else {
            this.firstMove = move;
        }
        return move;
    }
}
