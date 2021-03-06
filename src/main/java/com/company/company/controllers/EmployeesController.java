
package com.company.company.controllers;

import com.company.company.entities.EmployeeStatusCharacteristics;
import com.company.company.entities.Employee;
import com.company.company.exceptions.ErrorDataException;
import com.company.company.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeesController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/employees/add")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());

        return "add-employee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(Model model, @ModelAttribute Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            employeeService.addEmployee(employee);
            model.addAttribute("message", "Успешно добавен служител!");
        } catch (ErrorDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "add-employee";
    }

    @RequestMapping("/employees/all-employees")
    public String showEmployees(Model model) {
        model.addAttribute("allEmployees", employeeService.showEmployees());

        return "all-employees";
    }

    @GetMapping("/employees/characteristics")
    public String addCharacteristics(Model model) {
        model.addAttribute("employeeStatusCharacteristics", new EmployeeStatusCharacteristics());

        return "employees-characteristics";
    }

    @PostMapping("/employees/characteristics")
    public String addCharacteristics(Model model, @ModelAttribute EmployeeStatusCharacteristics employeeStatusCharacteristics,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            employeeService.setCharacteristics(employeeStatusCharacteristics);
            model.addAttribute("message", "Успешно добавена характеристика!");
        } catch (ErrorDataException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "employees-characteristics";
    }

    @RequestMapping("/created-details")
    public String showDetails(Model model, @RequestParam("id") Long id) {
        model.addAttribute("employeeDetails", employeeService.showEmployeeCreatedDetails(id));

        return "employee-details";
    }
}
