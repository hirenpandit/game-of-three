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

    LinkedHashMap<String, List<String>> gameMap = new LinkedHashMap<>();

    @Override
    public synchronized PlayerDTO addPlayer(String playerId) {
        LinkedList<Map.Entry<String, List<String>>> linkedList = new LinkedList<>(gameMap.entrySet());
        PlayerDTO player = null;
        if(linkedList.isEmpty()|| linkedList.getLast().getValue().size()==2){
            ArrayList<String> list = new ArrayList<>();
            list.add(playerId);
            String gameId = UUID.randomUUID().toString();
            gameMap.put(gameId, list);
            player = new PlayerDTO(gameId, playerId, true, 1);
        } else {
            Map.Entry<String, List<String>> last = linkedList.getLast();
            last.getValue().add(playerId);
            player = new PlayerDTO(last.getKey(), playerId, true, 2);
            publisher.publishEvent(new PlayerRegisterEvent(player));
        }
        return player;
    }

    @Override
    public synchronized void removePlayers(String gameId){
        gameMap.remove(gameId);
    }

    @Override
    public Optional<String> getRivalPlayer(String gameId, String playerId) {
        return gameMap.entrySet()
                    .stream()
                    .filter(gId -> gId.getKey().equals(gameId))
                    .flatMap(entry -> entry.getValue().stream())
                    .filter(pId -> !pId.equals(playerId))
                    .findFirst();
    }

}
