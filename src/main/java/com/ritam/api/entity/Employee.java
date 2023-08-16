package com.ritam.api.entity;

import com.ritam.api.annatation.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    @Size(min=5, message = "Username should have at least 5 characters!")
    private String username;

    @Column(name="first_name")
    @Size(min=2, message = "First name should have at least 2 characters!")
    private String firstName;

    @Column(name="last_name")
    @Size(min=2, message = "Last name should have at least 2 characters!")
    private String lastName;

    @Column(name="gender")
    @Gender
    private String gender;

    @Column(name="email")
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Please enter a valid email address!")
    private String email;

    @Column(name="date_of_birth")
    @Past(message = "Date of birth must be a past date!")
    private LocalDate dateOfBirth;
}
