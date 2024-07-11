package com.bcd.rockpaperfun.service;

import com.bcd.rockpaperfun.entity.TeamChoice;
import com.bcd.rockpaperfun.repository.TeamChoiceRepository;
import com.bcd.rockpaperfun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TeamChoiceServiceImpl implements TeamChoiceService {
    private final TeamChoiceRepository teamChoiceRepository;
    private final UserRepository userRepository;

    @Override
    public TeamChoice selectTeam(Long userId, String team) {
        TeamChoice teamChoice = new TeamChoice();
        teamChoice.setUserId(userId);
        teamChoice.setTeam(team);
        teamChoice.setDate(LocalDate.now());
        userRepository.findById(userId).ifPresent(user -> {
            user.setTeam(team);
            userRepository.save(user);
        });
        return teamChoiceRepository.save(teamChoice);
    }
}
