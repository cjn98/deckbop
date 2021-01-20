package com.deckbop.app.controller;

import com.deckbop.app.exception.UsernameTakenException;
import com.deckbop.app.controller.dto.RegisterPostRequest;
import com.deckbop.app.dao.UserDAO;
import com.deckbop.app.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LoggingService loggingService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterPostRequest registerDto) {
        try {
            userDAO.createUser(registerDto);
            loggingService.info("user: " + registerDto.getUsername() + " created.");
            return new ResponseEntity<>(HttpStatus.CREATED); // 201
        }
        catch (UsernameTakenException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
        }
    }
}
