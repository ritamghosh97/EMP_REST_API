package com.ritam.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ritam.api.annatation.OperationUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="team")
public class Team {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    @Size(min=3, message = "Team name should have at least 3 characters!")
    private String teamName;

    @Column(name="ou")
    @OperationUnit
    private String operationalUnit;

    @ManyToMany(fetch = FetchType.LAZY , cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name="employee_team",
            joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name = "emp_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<Employee> employee;
}
