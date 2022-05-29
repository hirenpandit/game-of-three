package com.takeaway.gameofthree.events;

import com.takeaway.gameofthree.dto.MoveDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AutoMoveEvent {

    private MoveDTO moveDTO;

}
