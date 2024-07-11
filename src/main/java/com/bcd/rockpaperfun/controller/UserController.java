package com.bcd.rockpaperfun.controller;

import com.bcd.rockpaperfun.dto.RPSVoteDto;
import com.bcd.rockpaperfun.dto.TeamChoiceDto;
import com.bcd.rockpaperfun.dto.UserDto;
import com.bcd.rockpaperfun.entity.RPSVote;
import com.bcd.rockpaperfun.entity.TeamChoice;
import com.bcd.rockpaperfun.entity.User;
import com.bcd.rockpaperfun.security.JwtTokenProvider;
import com.bcd.rockpaperfun.service.RPSVoteService;
import com.bcd.rockpaperfun.service.TeamChoiceService;
import com.bcd.rockpaperfun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TeamChoiceService teamChoiceService;
    private final RPSVoteService rpsVoteService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        User user = userService.signUp(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        authenticationManager.authenticate(authToken);
        String token = jwtTokenProvider.createToken(userDto.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/select-team")
    public ResponseEntity<?> selectTeam(@RequestBody TeamChoiceDto teamChoiceDto) {
        TeamChoice teamChoice = teamChoiceService.selectTeam(teamChoiceDto.getUserId(), teamChoiceDto.getTeam());
        return ResponseEntity.ok(teamChoice);
    }

    @PostMapping("/vote")
    public ResponseEntity<?> vote(@RequestBody RPSVoteDto rpsVoteDto) {
        RPSVote rpsVote = rpsVoteService.vote(rpsVoteDto.getTeam(), rpsVoteDto.getChoice());
        return ResponseEntity.ok(rpsVote);
    }
}
