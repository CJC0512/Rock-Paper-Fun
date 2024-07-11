package com.bcd.rockpaperfun.service;

import com.bcd.rockpaperfun.entity.RPSVote;
import com.bcd.rockpaperfun.repository.RPSVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RPSVoteServiceImpl implements RPSVoteService {
    private final RPSVoteRepository rpsVoteRepository;

    @Override
    public RPSVote vote(String team, String choice) {
        RPSVote rpsVote = new RPSVote();
        rpsVote.setTeam(team);
        rpsVote.setChoice(choice);
        rpsVote.setDate(LocalDate.now());
        return rpsVoteRepository.save(rpsVote);
    }
}
