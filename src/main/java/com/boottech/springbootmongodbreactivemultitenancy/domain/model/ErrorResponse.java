package com.boottech.springbootmongodbreactivemultitenancy.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {

    private String code;
    private String message;
    private String description;
    private Object errors;
}
