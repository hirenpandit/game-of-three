package com.takeaway.gameofthree.events;

import com.takeaway.gameofthree.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerRegisterEvent {
    PlayerDTO playerDTO;
}
