package com.core.resource;

import com.core.dto.TodoCreateDto;
import com.core.dto.TodoUpdateDto;
import com.core.entities.Todo;
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
    public ResponseEntity<Todo> create(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody @Valid TodoCreateDto todoDto) {
        return ResponseEntity.ok(todoService.createNewTodo(todoDto, token));
    }


    @GetMapping(value = "/page")
    public ResponseEntity<Page<Todo>> pageFindAllTodo(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage
            ) {
        return ResponseEntity.ok(todoService.findPageAllByUserTodo(token, page, linesPerPage));
    }


    @PutMapping
    public ResponseEntity<Todo> todoUpdate(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody @Valid TodoUpdateDto todoDto) {
        return ResponseEntity.ok(todoService.updateTodo(token, todoDto));
    }


    //#fazer delet com marina
}
