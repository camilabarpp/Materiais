package com.materiais.materiais.configuration.errorresponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.materiais.materiais.configuration.errorobject.ErrorObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {
   @JsonFormat(pattern = "dd-MM-yyyy" + " " + "'T'HH:mm:ss")
private LocalDateTime timestamp;
    private List<ErrorObject> error;
}


