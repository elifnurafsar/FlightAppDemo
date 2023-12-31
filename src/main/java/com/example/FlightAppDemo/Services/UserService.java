package com.example.FlightAppDemo.Services;

import com.example.FlightAppDemo.Config.JwtService;
import com.example.FlightAppDemo.DTO.User.AuthenticationDTO;
import com.example.FlightAppDemo.DTO.User.UserDTO;
import com.example.FlightAppDemo.Entities.User.User;
import com.example.FlightAppDemo.Mappers.UserMapper;
import com.example.FlightAppDemo.Repositories.UserRepository;
import com.example.FlightAppDemo.Responses.User.AuthenticationResponse;
import com.example.FlightAppDemo.Responses.User.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository _userRepository, JwtService _jwtService, AuthenticationManager _authenticationManager) {
        this.userRepository = _userRepository;
        this.jwtService = _jwtService;
        this.authenticationManager = _authenticationManager;
    }

    @Transactional
    public AuthenticationResponse createUser(UserDTO new_user){
        new_user.setPassword(new BCryptPasswordEncoder().encode(new_user.getPassword()));
        userRepository.insertUser(new_user);
        User user = userRepository.getByEmail(new_user.getUsername()).orElse(null);
        if(user != null){
            var jwt_token = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwt_token)
                    .build();
        }
        else
            return null;
    }

    public AuthenticationResponse authenticate(AuthenticationDTO user) {
        //CONTROL
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        User _user = userRepository.getByEmail(user.getUsername()).orElseThrow();
        if(_user.isEnabled()){
            var jwt_token = jwtService.generateToken(_user);
            return AuthenticationResponse.builder()
                    .token(jwt_token)
                    .build();
        }
        else return null;
    }


    public UserResponse getUserByUserName(String e_mail){
        User user = userRepository.getByEmail(e_mail).orElse(null);
        if(user != null){
            return UserMapper.INSTANCE.entityToResponse(user);
        }
        else
            return null;
    }

    public List<UserResponse> getAll(){
        List<User> list = userRepository.getAll();
        return UserMapper.INSTANCE.entityListToResponseList(list);
    }

    public boolean deleteUserByUserName(String e_mail){
        userRepository.deleteSelf(e_mail);
        Optional<User> _admin = userRepository.getByEmail(e_mail);
        if(_admin.isPresent())
            return false;
        return true;
    }

    public boolean EnableAccount(String e_mail) {
        User _user = userRepository.getByEmail(e_mail).orElse(null);
        if(_user != null){
            int a = userRepository.EnableAccount(_user.getUsername());
            if(a < 0)
                return false;
            return true;
        }
        else
            return false;
    }

}

