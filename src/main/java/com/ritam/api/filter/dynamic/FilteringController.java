package com.ritam.api.filter.dynamic;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/filter")
public class FilteringController {

    @GetMapping("/no-filtering")
    public MappingJacksonValue noFiltering(){
        Employee employee = new Employee("Ritam",
                "Ghosh", "ritamghosh97@gmail.com", "+91 9933725498");
        MappingJacksonValue jacksonValue = new MappingJacksonValue(employee);

        PropertyFilter empPhFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("firstName", "lastName", "email", "phone");

        FilterProvider employeeFilterProvider =
                new SimpleFilterProvider().addFilter("EMPLOYEE_FILTER", empPhFilter);

        jacksonValue.setFilters(employeeFilterProvider);

        return jacksonValue;
    }

    @GetMapping("/filter-phone")
    public MappingJacksonValue filterPhone(){
        Employee employee = new Employee("Ritam",
                "Ghosh", "ritamghosh97@gmail.com", "+91 9933725498");
        MappingJacksonValue jacksonValue = new MappingJacksonValue(employee);

        PropertyFilter empPhFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("firstName", "lastName", "email");

        FilterProvider employeeFilterProvider = new SimpleFilterProvider()
                .addFilter("EMPLOYEE_FILTER", empPhFilter);

        jacksonValue.setFilters(employeeFilterProvider);

        return jacksonValue;
    }

    @GetMapping("/filter-email-list")
    public MappingJacksonValue filterList(){

        List<Employee> employees = Arrays.asList(
                new Employee("Ritam", "Ghosh",
                        "ritamghosh97@gmail.com", "+91 9933725498"),
                new Employee("Soumalya", "Samanta",
                        "soumalyasamanta99@gmail.com", "+91 7656353723"),
                new Employee("Debrina", "Dutta",
                        "debrinadutta2013@gmail.com", "+91 8786536342"),
                new Employee("Sukanya", "Roy",
                        "sukanya.roy@gmail.com", "+91 738453836")
        );

        MappingJacksonValue jacksonValue = new MappingJacksonValue(employees);

        PropertyFilter empPhFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("firstName", "lastName", "phone");

        FilterProvider employeeFilterProvider = new SimpleFilterProvider()
                .addFilter("EMPLOYEE_FILTER", empPhFilter);

        jacksonValue.setFilters(employeeFilterProvider);

        return jacksonValue;
    }
}
