package com.core.dto;


import com.core.entities.Task;
import com.core.entities.Todo;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {

    private UUID id;
    private UUID userId;
    private String title;
    private List<Task> task;
    private Date createdDate;
    private Date lastUpdatedDate;
}
