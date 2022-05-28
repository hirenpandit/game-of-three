package com.takeaway.gameofthree.events;

import com.takeaway.gameofthree.dto.MoveDTO;
import com.takeaway.gameofthree.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerRegisterEventListener {

    private final GameServiceImpl gameService;

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleEvent(PlayerRegisterEvent event){
        log.info("player register event fired");
        gameService
                 .getFirstMove()
                 .ifPresent(move -> {
                     MoveDTO moveDTO = gameService.playMove(move);
                     messagingTemplate.convertAndSend("/topic/"+moveDTO.getPlayerId()+"/move", moveDTO);
                 });

    }

}
