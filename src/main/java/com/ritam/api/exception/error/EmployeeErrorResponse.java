package com.ritam.api.exception.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class EmployeeErrorResponse {

    private LocalDateTime timestamp;

    private Integer statusCode;

    private String httpStatus;

    private String message;

    private String path;
}
