package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.MoveDTO;
import com.takeaway.gameofthree.events.GameOverEvent;
import com.takeaway.gameofthree.events.MoveEvent;
import com.takeaway.gameofthree.events.WaitEvent;
import com.takeaway.gameofthree.events.StatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;

    private final ApplicationEventPublisher publisher;

    @Override
    public void playMove(MoveDTO move) {
        String gameId = move.getGameId();
        Optional<String> optionalId = playerService.getRivalPlayer(
                gameId,
                move.getPlayerId());
        if(optionalId.isPresent()) {
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
                publisher.publishEvent(new GameOverEvent(moveDTO.getPlayerId(), move.getPlayerId(), true));
            }
            publisher.publishEvent(new MoveEvent(moveDTO));
        } else {
            publisher.publishEvent(new WaitEvent(true));
        }
    }
}
