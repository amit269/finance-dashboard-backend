package com.finance.dashboard.user.controller;
import com.finance.dashboard.user.dto.UserRequest;
import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.serviceinterfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest,
     @RequestHeader("X-User-Id") Long userId){
        UserResponse user = userService.createUser(userRequest,userId);
        return user;
    }
    @GetMapping
    public List<UserResponse> getAllUser(){
        List<UserResponse> allUser = userService.getAllUsers();
        return allUser;

    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        return userService.updateUser(id, request , userId);
    }
    @DeleteMapping("/{id}")
    public UserResponse deactivateUser(@PathVariable Long id,
                                       @RequestHeader("X-User-Id") Long userId) {
        return userService.deactivateUser(id , userId);
    }
}
