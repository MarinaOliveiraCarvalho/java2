package com.core.dto;


import com.core.entities.Task;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoUpdateDto {

    @NotBlank
    private String id;
    @NotBlank
    private String title;
}
