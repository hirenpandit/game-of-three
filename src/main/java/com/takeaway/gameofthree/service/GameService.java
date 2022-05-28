package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.MoveDTO;

import java.util.Optional;

public interface GameService {

    String ADD_ZERO =  "added 0";
    String ADD_ONE = "added 1";
    String SUBTRACT_ONE = "subtracted 1";
    MoveDTO playMove(MoveDTO move);

    void firstMove(MoveDTO move);

    Optional<MoveDTO> getFirstMove();

}
