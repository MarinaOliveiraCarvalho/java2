package com.core.repositories;

import com.core.entities.Todo;
import com.core.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    Page<Todo> findAllByUser(User user, Pageable pageable);

    Optional<Todo> findByIdAndUser(UUID id, User user);
}
