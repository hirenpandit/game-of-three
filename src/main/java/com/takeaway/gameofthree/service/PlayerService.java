package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.PlayerDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PlayerService {

    void addPlayer(String playerId);

    void removePlayers(String gameId);

    Optional<String> getRivalPlayer(String gameId, String playerId);
}
