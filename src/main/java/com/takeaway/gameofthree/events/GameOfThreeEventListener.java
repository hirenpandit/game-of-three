package com.takeaway.gameofthree.events;

import com.takeaway.gameofthree.dto.GameOverDTO;
import com.takeaway.gameofthree.service.GameServiceImpl;
import com.takeaway.gameofthree.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameOfThreeEventListener {

    private final GameServiceImpl gameService;
    private final PlayerService playerService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleEvent(PlayerRegisterEvent event){
        log.info("player register event fired");
        Optional<String> rivalPlayer = playerService
                .getRivalPlayer(event.getPlayerDTO().getGameId(), event.getPlayerDTO().getPlayerId());
        messagingTemplate.convertAndSend("/topic/"+event.playerDTO.getPlayerId()+"/play", event.getPlayerDTO());
        if(rivalPlayer.isPresent()) {
            handleEvent(new StatusEvent(rivalPlayer.get(), "Ready to play"));
            handleEvent(new StatusEvent(event.playerDTO.getPlayerId(), "Ready to play"));
        } else {
            handleEvent(new StatusEvent(event.getPlayerDTO().getPlayerId(), "Waiting for 2nd player"));
        }

    }

    @EventListener
    public void handleEvent(StatusEvent event){
        log.info("win-lose event fired");
        messagingTemplate.convertAndSend("/topic/"+event.getPlayerId()+"/status", event);
    }

    @EventListener
    public void handleMoveEvent(MoveEvent event) {
        messagingTemplate.convertAndSend(
                "/topic/"+event.getMoveDTO().getPlayerId()+"/move",
                event.getMoveDTO()
        );
    }

    @EventListener
    public void handleEvent(GameOverEvent event){
        log.info("game is over! winner: {} loser: {}", event.getWinnerId(), event.getLoserId());
        messagingTemplate.convertAndSend(
                "/topic/"+event.winnerId+"/win-lose",
                new GameOverDTO(true, "You Win🎉")
        );
        messagingTemplate.convertAndSend(
                "/topic/"+event.loserId+"/win-lose",
                new GameOverDTO(false, "You Lose!")
        );
    }

}
