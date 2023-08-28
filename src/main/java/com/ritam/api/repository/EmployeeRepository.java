package com.ritam.api.repository;

import com.ritam.api.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "insert into employee (id, username, " +
            "first_name, last_name, gender, email, date_of_birth) " +
            "values (:#{#emp.id}, :#{#emp.username}, :#{#emp.firstName}, " +
            ":#{#emp.lastName}, :#{#emp.gender}, :#{#emp.email}, " +
            ":#{#emp.dateOfBirth})", nativeQuery = true)
    void saveEmployee(@Param("emp") Employee employee);
}
