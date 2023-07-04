package com.core.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskConclusionDto {

    @NotNull(message = "Id may not be null")
    @NotBlank(message = "Id may not be blank")
    private String id;

    @NotNull(message = "Name may not be null")
    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotNull(message = "TodoId may not be null")
    @NotBlank(message = "TodoId may not be blank")
    private String todoId;
}
