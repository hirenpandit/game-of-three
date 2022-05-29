package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.MoveDTO;

import java.util.Optional;

public interface GameService {

    String ADD_ZERO =  "added 0";
    String ADD_ONE = "added 1";
    String SUBTRACT_ONE = "added -1";
    void playMove(MoveDTO move);

}
