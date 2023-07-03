package com.core.resource;

import com.core.dto.TodoDto;
import com.core.dto.UserDto;
import com.core.entities.Todo;
import com.core.entities.User;
import com.core.service.LoginService;
import com.core.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/todo")
public class TodoResource {

    @Autowired
    TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody @Valid TodoDto todoDto) {
        return ResponseEntity.ok(todoService.createNewTodo(todoDto));
    }


    @GetMapping(value = "/page")
    public ResponseEntity<Page<Todo>> pageFindAllTodo(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
            ) {
        return ResponseEntity.ok(todoService.findPageAllByUserTodo(token, page, linesPerPage, orderBy, direction ));
    }



    //#fazer delet com marina
}
