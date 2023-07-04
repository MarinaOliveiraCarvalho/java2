package com.core.service;

import com.core.dto.TaskCreateDto;
import com.core.dto.TodoCreateDto;
import com.core.entities.Task;
import com.core.entities.Todo;
import com.core.entities.User;
import com.core.repositories.TaskRepository;
import com.core.repositories.TodoRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private OauthService oauthService;

    @Autowired
    private TodoService todoService;

    public Task createNewTask(TaskCreateDto taskDto, String token){
        User user = this.oauthService.getUserByToken(token);
        try {
            Todo todo = Todo.builder().id(UUID.fromString(taskDto.getTodoId())).build();
            this.todoService.findOneTodoOfUser(todo.getId(), user);

            return taskRepository.save(Task.builder()
                    .todo(todo)
                    .name(taskDto.getName())
                    .conclusion(taskDto.getConclusion())
                    .createdDate(LocalDateTime.now())
                    .lastUpdatedDate(LocalDateTime.now())
                    .build());
        }catch (ResponseStatusException n){
            log.error(n.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERROR Not found Todo in DB with id: " + taskDto.getTodoId());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on create new Task to User: " + user.getId());
        }
    }
}
