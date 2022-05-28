package com.takeaway.gameofthree.controller;

import com.takeaway.gameofthree.dto.MoveDTO;
import com.takeaway.gameofthree.dto.PlayerDTO;
import com.takeaway.gameofthree.service.GameServiceImpl;
import com.takeaway.gameofthree.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameServiceImpl gameService;

    private final PlayerService playerService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/first-move")
    public void firstMove(MoveDTO move){
        gameService.firstMove(move);
    }

    @MessageMapping("/move")
    public void game(MoveDTO move){
        log.info("received move {}", move);
        MoveDTO playedMove = gameService.playMove(move);
        messagingTemplate.convertAndSend(
                "/topic/"+playedMove.getPlayerId()+"/move",
                playedMove
        );
    }

    @MessageMapping("/play")
    public void play(String playerId){
        PlayerDTO player = null;
        try {
            player = playerService.addPlayer(playerId);
        } catch(IllegalStateException ex){
            log.error("unable to add player: {}",ex);
            player = new PlayerDTO(null, playerId, false, 0);
        }
        messagingTemplate.convertAndSend("/topic/"+playerId+"/play", player);
    }

}
