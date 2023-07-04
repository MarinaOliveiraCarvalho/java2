package com.core.service;

import com.core.dto.TodoCreateDto;
import com.core.dto.TodoUpdateDto;
import com.core.entities.Todo;
import com.core.entities.User;
import com.core.repositories.TodoRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private OauthService oauthService;

    public Todo createNewTodo(TodoCreateDto todoDto, String token){
        User user = this.oauthService.getUserByToken(token);
        try {
            return todoRepository.save(Todo.builder()
                    .user(user)
                    .title(todoDto.getTitle())
                    .createdDate(LocalDateTime.now())
                    .lastUpdatedDate(LocalDateTime.now())
                    .build());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on create new Todo to User: " + user.getId());
        }
    }

    public Page<Todo> findPageAllByUserTodo(String token, Integer page, Integer linesPerPage){
        User user = this.oauthService.getUserByToken(token);
        try {
            Pageable paging = PageRequest.of(page, linesPerPage);
            Page<Todo> todos = todoRepository.findAllByUser(user, paging);

            todos.stream().forEach( obj -> {
                obj.setUser(user);
            });

            return todos;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on find pages of Todo this Token-User: " + user.getId());
        }
    }

    public Todo updateTodo(String token, TodoUpdateDto todoDto){
        try {
            User user = this.oauthService.getUserByToken(token);

            Todo todo = findOneTodoOfUser(UUID.fromString(todoDto.getId()), user);

            todo.setTitle(todoDto.getTitle());
            todo.setLastUpdatedDate(LocalDateTime.now());

            return todoRepository.save(todo);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on update in Todo of Id: " + todoDto.getId());
        }
    }

    public Todo findOneTodoOfUser(UUID todoId, User user) throws NotFoundException{
        try {
            return todoRepository.findByIdAndUser(todoId, user).orElseThrow(
                    () -> new NotFoundException("Not Found Todo")
            );
        }catch (NotFoundException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ERROR Not found Todo in DB with id: " + todoId);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("ERROR In found Todo in DB with id: " + todoId);
        }
    }
}
