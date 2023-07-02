package com.users.service;

import com.users.dto.UserDto;
import com.users.entities.Role;
import com.users.entities.User;
import com.users.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createNewUser(UserDto userDto){
        try{
            log.info("Begin create new user {}", userDto.getEmail());
            if(!isValidateNewUser(userDto)){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                        "ERROR User email already exists in DB  with email: " + userDto.getEmail());
            }
            String password = bCryptPasswordEncoder.encode(userDto.getPassword());
            User userPreLoad = new User(userDto.getName(),userDto.getEmail(),password);
            User user = userRepository.save(userPreLoad);
            user.setPassword("******");
            return user;
        }catch (ResponseStatusException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "ERROR User email already exists in DB  with email: " + userDto.getEmail());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on create new User with email: " + userDto.getEmail());
        }
    }

    private Boolean isValidateNewUser(UserDto userDto){
        try {
            if(!isNull(existUserDtoByEmail(userDto))){
                throw new RuntimeException("User email already exists in DB !!!");
            }

            return true;
        }catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "ERROR User email already exists in DB  with email: " + userDto.getEmail());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on create new User with email: " + userDto.getEmail());
        }
    }

    private User existUserDtoByEmail(UserDto userDto){
        try {
            return userRepository.findByEmail(userDto.getEmail()).orElse(null);
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public User findUserByEmail(String email){
        try {
            return userRepository.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("Not Found User email")
            );
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERROR User email not found in DB with email: " + email);
        }
    }
}
