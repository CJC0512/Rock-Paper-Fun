package com.bcd.rockpaperfun.service;
import com.bcd.rockpaperfun.entity.User;
import com.bcd.rockpaperfun.repository.RPSVoteRepository;
import com.bcd.rockpaperfun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RPSVoteRepository rpsVoteRepository;
    private final UserRepository userRepository;
    private Object RPSVote;

    public void calculateAndApplyResults() {
        // 각 진영의 투표 비율 계산
        Map<String, Map<String, Long>> voteCounts = rpsVoteRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        RPSVote::getTeam,
                        Collectors.groupingBy(
                                RPSVote::getChoice,
                                Collectors.counting()
                        )
                ));

        // 각 팀의 선택 확률 계산
        Map<String, Double> teamAProbs = calculateProbabilities(voteCounts.get("A"));
        Map<String, Double> teamBProbs = calculateProbabilities(voteCounts.get("B"));

        // 결과 계산 (예시: 랜덤 선택)
        String teamAChoice = getRandomChoice(teamAProbs);
        String teamBChoice = getRandomChoice(teamBProbs);

        // 승리 팀 결정
        String winningTeam = determineWinner(teamAChoice, teamBChoice);

        // 결과 적용
        applyResults(winningTeam);
    }

    private Map<String, Double> calculateProbabilities(Map<String, Long> voteCount) {
        // 전체 투표 수
        long totalVotes = voteCount.values().stream().mapToLong(Long::longValue).sum();
        // 각 선택의 확률 계산
        return voteCount.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() / (double) totalVotes));
    }

    private String getRandomChoice(Map<String, Double> probabilities) {
        double random = Math.random();
        double cumulativeProbability = 0.0;
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (random <= cumulativeProbability) {
                return entry.getKey();
            }
        }
        return "가위"; // 기본값
    }

    private String determineWinner(String choiceA, String choiceB) {
        if (choiceA.equals(choiceB)) return "Draw";
        if ((choiceA.equals("가위") && choiceB.equals("보")) ||
                (choiceA.equals("바위") && choiceB.equals("가위")) ||
                (choiceA.equals("보") && choiceB.equals("바위"))) {
            return "A";
        } else {
            return "B";
        }
    }

    private void applyResults(String winningTeam) {
        if (winningTeam.equals("Draw")) return;

        List<User> winners = userRepository.findAll().stream()
                .filter(user -> user.getTeam().equals(winningTeam))
                .collect(Collectors.toList());

        List<User> losers = userRepository.findAll().stream()
                .filter(user -> !user.getTeam().equals(winningTeam))
                .collect(Collectors.toList());

        winners.forEach(user -> {
            user.setWinStreak(user.getWinStreak() + 1);
            user.setPoints(user.getPoints() + 10); // 예시 포인트 증가
            userRepository.save(user);
        });

        losers.forEach(user -> {
            user.setWinStreak(0);
            user.setPoints(user.getPoints() + 5); // 예시 포인트 증가
            userRepository.save(user);
        });
    }
}