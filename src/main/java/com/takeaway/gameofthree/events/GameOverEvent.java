package com.takeaway.gameofthree.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GameOverEvent {
    String winnerId;
    String loserId;
    boolean isWin;
}
