package com.bcd.rockpaperfun.service;

import com.bcd.rockpaperfun.entity.TeamChoice;

public interface TeamChoiceService {
    TeamChoice selectTeam(Long userId, String team);

}
