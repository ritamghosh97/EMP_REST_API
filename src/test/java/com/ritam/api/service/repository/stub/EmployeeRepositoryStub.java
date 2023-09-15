package com.ritam.api.service.repository.stub;

import com.ritam.api.entity.Employee;
import com.ritam.api.repository.EmployeeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EmployeeRepositoryStub implements EmployeeRepository {

    private static List<Employee>  employees = Arrays.asList(
            new Employee(1, "ritamghosh97", "Ritam", "Ghosh", "M", "ritamghosh97@gmail.com", LocalDate.of(1997, 3, 10), new ArrayList<>()),
            new Employee(2, "debrinadutta96", "Debrina", "Dutta", "F", "debrinadutta2013@gmail.com", LocalDate.of(1996, 7, 3), new ArrayList<>()),
            new Employee(3, "arpankarmakar95", "Arpan", "Karmakar", "M", "arpankarmakar95@gmail.com", LocalDate.of(1996, 2, 21), new ArrayList<>())
    );

    @Override
    public Employee findByUsername(String username) {
        return null;
    }

    @Override
    public void saveEmployee(Employee employee) {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Employee> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Employee> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Employee> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Employee getOne(Integer integer) {
        return null;
    }

    @Override
    public Employee getById(Integer integer) {
        return null;
    }

    @Override
    public Employee getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Employee> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Employee> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Employee> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Employee> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Employee> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Employee> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Employee, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Employee> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Employee> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Employee> findById(Integer integer) {
        Optional<Employee> employee = employees.stream().filter(emp -> emp.getId()==integer).findFirst();
        return employee;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public List<Employee> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Employee entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Employee> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Employee> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return null;
    }
}
