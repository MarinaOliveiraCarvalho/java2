package com.core.resource;

import com.core.dto.TaskCreateDto;
import com.core.dto.TaskUpdateDto;
import com.core.dto.TodoCreateDto;
import com.core.dto.TodoUpdateDto;
import com.core.entities.Task;
import com.core.entities.Todo;
import com.core.entities.User;
import com.core.service.TaskService;
import com.core.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskResource {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody @Valid TaskCreateDto taskDto) {
        return ResponseEntity.ok(taskService.createNewTask(taskDto, token));
    }

    @GetMapping(value = "/page/{todoId}")
    public ResponseEntity<Page<Task>> pageFindAllTodo(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable UUID todoId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage
    ) {
        return ResponseEntity.ok(taskService.findAllPageByUserAndTask(token, todoId, page, linesPerPage));
    }

    @PutMapping
    public ResponseEntity<Task> taskUpdate(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody @Valid TaskUpdateDto taskUpdateDto) {
        return ResponseEntity.ok(taskService.updateTask(token, taskUpdateDto));
    }

}
