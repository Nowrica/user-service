package com.project.userservice.controller;

import com.project.userservice.dto.UserDto;
import com.project.userservice.jpa.UserEntity;
import com.project.userservice.service.UserService;
import com.project.userservice.vo.RequestUser;
import com.project.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;

    private final UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status() {
        return env.getProperty("local.server.port");
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto serviceUser = userService.createUser(userDto);
        ResponseUser responseUser = modelMapper.map(serviceUser, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userByAll = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userByAll.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String userId) {
        ResponseUser result = new ModelMapper().map(userService.getUserByUserId(userId), ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
