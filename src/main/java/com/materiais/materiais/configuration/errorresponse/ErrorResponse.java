package com.materiais.materiais.configuration.errorresponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorResponse {
    private String message;
    private String field;
    private String parameter;
   @JsonFormat(pattern = "dd-MM-yyyy" + " " + "'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
