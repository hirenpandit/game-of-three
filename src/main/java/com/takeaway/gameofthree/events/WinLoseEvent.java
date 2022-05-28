package com.takeaway.gameofthree.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WinLoseEvent {

    boolean isWin;

    String playerId;

}
