package com.takeaway.gameofthree.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusEvent {

    String playerId;

    String message;

}
