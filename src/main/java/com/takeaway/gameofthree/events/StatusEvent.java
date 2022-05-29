package com.takeaway.gameofthree.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StatusEvent {
    String playerId;
    String message;
    boolean isReady;
}
