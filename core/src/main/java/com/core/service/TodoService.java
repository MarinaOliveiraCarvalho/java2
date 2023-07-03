package com.core.service;

import com.core.dto.TodoDto;
import com.core.dto.UserDto;
import com.core.dto.oauth.UserTokenDTO;
import com.core.entities.Todo;
import com.core.entities.User;
import com.core.repositories.TodoRepository;
import com.core.repositories.UserRepository;
import com.sun.javafx.scene.traversal.Direction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private OauthService oauthService;

    public Todo createNewTodo(TodoDto todoDto){
        try {

            User user = new User();
            user.setId(todoDto.getUserId());

            return todoRepository.save(Todo.builder()
                    .user(user)
                    .title(todoDto.getTitle())
                    .createdDate(new Date())
                    .lastUpdatedDate(new Date())
                    .build());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on create new Todo to User: " + todoDto.getUserId());
        }
    }

    public Page<Todo> findPageAllByUserTodo(String token, Integer page, Integer linesPerPage, String orderBy, String direction){
        try {
            UserTokenDTO userTokenDTO = this.oauthService.getUserData(token);
            User user = new User();
            user.setId(userTokenDTO.getId());

            PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

            Pageable paging = PageRequest.of(page, linesPerPage);
            Page<Todo> todos = todoRepository.findAllByUser(user, paging);

            todos.stream().forEach( obj -> {
                obj.setUser(user);
            });

            return todos;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR on find pages of Todo this Token-User: " + token);
        }
    }
}
