package com.materiais.materiais.configuration.errorobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorObject {
    private String message;
    private String field;
    private String parameter;
}


