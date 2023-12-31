package com.example.FlightAppDemo.Controllers;


import com.example.FlightAppDemo.DTO.User.AuthenticationDTO;
import com.example.FlightAppDemo.DTO.User.UserDTO;
import com.example.FlightAppDemo.Responses.User.AuthenticationResponse;
import com.example.FlightAppDemo.Responses.User.UserResponse;
import com.example.FlightAppDemo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService _userService) {
        this.userService = _userService;
    }

    @GetMapping("/get_all")
    public List<UserResponse> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("/get_by_email")
    public UserResponse getUserByEMail(@RequestParam("e_mail") String e_mail){
        return userService.getUserByUserName(e_mail);
    }

    // REGISTER
    @PostMapping
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody UserDTO new_user){
        return ResponseEntity.ok(userService.createUser(new_user));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDTO user){
        return ResponseEntity.ok(userService.authenticate(user));
    }

    @DeleteMapping
    public boolean DeleteUser(@RequestParam("e_mail") String e_mail){
        return userService.deleteUserByUserName(e_mail);
    }

    @PutMapping("/enable_account")
    public boolean enableAccount(@RequestParam("e_mail") String e_mail){
        return userService.EnableAccount(e_mail);
    }
}

