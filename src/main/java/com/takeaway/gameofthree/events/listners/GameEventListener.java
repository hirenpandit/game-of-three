package com.takeaway.gameofthree.events.listners;

import com.takeaway.gameofthree.dto.GameOverDTO;
import com.takeaway.gameofthree.events.AutoMoveEvent;
import com.takeaway.gameofthree.events.GameOverEvent;
import com.takeaway.gameofthree.events.MoveEvent;
import com.takeaway.gameofthree.events.StatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameEventListener {


    private final SimpMessagingTemplate messagingTemplate;



    @EventListener
    public void handleEvent(StatusEvent event){
        log.info("win-lose event fired");
        messagingTemplate.convertAndSend("/topic/"+event.getPlayerId()+"/status", event);
    }

    @EventListener
    public void handleEvent(MoveEvent event) {
        messagingTemplate.convertAndSend(
                "/topic/"+event.getMoveDTO().getPlayerId()+"/move",
                event.getMoveDTO()
        );
    }

    @EventListener
    public void handleEvent(GameOverEvent event){
        log.info("game is over! winner: {} loser: {}", event.getWinnerId(), event.getLoserId());
        messagingTemplate.convertAndSend(
                "/topic/"+event.getWinnerId()+"/win-lose",
                new GameOverDTO(true, "You Win🎉")
        );
        messagingTemplate.convertAndSend(
                "/topic/"+event.getLoserId()+"/win-lose",
                new GameOverDTO(false, "You Lose!")
        );
    }

    @EventListener
    public void handleEvent(AutoMoveEvent event){
        messagingTemplate.convertAndSend(
                "/topic/"+event.getMoveDTO().getPlayerId()+"/auto",
                event.getMoveDTO()
        );
    }

}
