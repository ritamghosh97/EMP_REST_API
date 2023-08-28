package com.ritam.api.filter.dynamic;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter(value="EMPLOYEE_FILTER")
public class Employee {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
