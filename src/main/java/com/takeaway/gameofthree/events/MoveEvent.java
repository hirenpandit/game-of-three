package com.takeaway.gameofthree.events;

import com.takeaway.gameofthree.dto.MoveDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveEvent {

    MoveDTO moveDTO;

}
