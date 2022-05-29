package com.takeaway.gameofthree.events.listners;

import com.takeaway.gameofthree.events.PlayerRegisterEvent;
import com.takeaway.gameofthree.events.StatusEvent;
import com.takeaway.gameofthree.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerEventListener {

    private final PlayerService playerService;
    private final SimpMessagingTemplate messagingTemplate;

    private final ApplicationEventPublisher publisher;

    @EventListener
    public void handleEvent(PlayerRegisterEvent event){
        log.info("player register event fired");
        Optional<String> rivalPlayer = playerService
                .getRivalPlayer(event.getPlayerDTO().getGameId(), event.getPlayerDTO().getPlayerId());
        messagingTemplate.convertAndSend("/topic/"+event.getPlayerDTO().getPlayerId()+"/play", event.getPlayerDTO());
        if(rivalPlayer.isPresent()) {
            publisher.publishEvent(new StatusEvent(rivalPlayer.get(), "Ready to play"));
            publisher.publishEvent(new StatusEvent(event.getPlayerDTO().getPlayerId(), "Ready to play"));
        } else {
            publisher.publishEvent(new StatusEvent(event.getPlayerDTO().getPlayerId(), "Waiting for 2nd player"));
        }

    }

}
