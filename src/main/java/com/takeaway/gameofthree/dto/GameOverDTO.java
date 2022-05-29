package com.takeaway.gameofthree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameOverDTO {

    boolean isWin;
    String message;

}
