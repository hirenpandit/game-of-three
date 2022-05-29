package com.takeaway.gameofthree.service;

import com.takeaway.gameofthree.dto.MoveDTO;

import java.util.Optional;

public interface GameService {

    String ADD_ZERO =  "0";
    String ADD_ONE = "1";
    String SUBTRACT_ONE = "-1";
    void playMove(MoveDTO move);

    void autoMove(MoveDTO move);

}
