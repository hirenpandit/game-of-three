package com.takeaway.gameofthree.controller;

import com.takeaway.gameofthree.dto.PlayerDTO;
import com.takeaway.gameofthree.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PlayerController {

    private final PlayerService playerService;

    @MessageMapping("/play")
    public void play(String playerId){
        PlayerDTO player = null;
        try {
            playerService.addPlayer(playerId);
        } catch(IllegalStateException ex){
            log.error("unable to add player: {}",ex);
        }
    }

}
