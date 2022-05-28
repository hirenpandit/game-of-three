package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.PlayerDTO;
import com.takeaway.gameofthree.events.PlayerRegisterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService{

    private final ApplicationEventPublisher publisher;
    Set<String> players = new LinkedHashSet<>();

    Stack<LinkedHashSet<String>> gameStack = new Stack<>();

    @Override
    public synchronized PlayerDTO addPlayer(String playerId) {
        if(players.size() == 2){
            throw new IllegalStateException("Only two player can play game at a time!");
        }
        players.add(playerId);
        log.info("new player added with id : {}", playerId);
        PlayerDTO playerDTO = new PlayerDTO(playerId, true, players.size());
        if(players.size() == 2) {
            publisher.publishEvent(new PlayerRegisterEvent(playerDTO));
        }
        return playerDTO;
    }

    @Override
    public synchronized void removePlayers(){
        players.clear();
    }

    @Override
    public Optional<String> getRivalPlayer(String playerId) {
        return players
                .stream()
                .filter(id -> !id.equals(playerId))
                .findFirst();
    }

}
