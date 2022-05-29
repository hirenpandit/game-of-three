package com.takeaway.gameofthree.controller;

import com.takeaway.gameofthree.dto.MoveDTO;
import com.takeaway.gameofthree.service.impl.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameServiceImpl gameService;

    @MessageMapping("/move")
    public void playedMove(MoveDTO move){
        log.info("received move {}", move);
        gameService.playMove(move);
    }

    @MessageMapping("/auto")
    public void autoMove(MoveDTO move){
        log.info("auto move played: {}", move);
        gameService.autoMove(move);
    }

}
