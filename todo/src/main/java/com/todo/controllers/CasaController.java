package com.todo.controllers;

import com.todo.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cads")
public class CasaController {

    @Autowired
    TestService testService;

    @GetMapping
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok(testService.test());
    }
}
