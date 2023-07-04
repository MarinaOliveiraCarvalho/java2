package com.core.dto;

import com.core.entities.Todo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoAllForAdmin {

    private List<Todo> a;
}
