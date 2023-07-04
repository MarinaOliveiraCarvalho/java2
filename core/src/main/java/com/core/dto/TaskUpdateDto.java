package com.core.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateDto {

    @NotNull(message = "Id may not be null")
    @NotBlank(message = "Id may not be blank")
    private String id;

    @NotNull(message = "TodoId may not be null")
    @NotBlank(message = "TodoId may not be blank")
    private String todoId;

    @NotNull(message = "Name may not be null")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Conclusion may not be null")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime conclusion;
}
