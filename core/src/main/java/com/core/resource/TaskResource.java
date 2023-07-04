package com.core.resource;

import com.core.dto.TaskCreateDto;
import com.core.dto.TodoCreateDto;
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

}
