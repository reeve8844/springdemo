package com.example.springdemo.controller;

import com.example.springdemo.dto.EmployeeWithRoleDTO;
import com.example.springdemo.entity.Employee_Roles;
import com.example.springdemo.entity.Employees;
import com.example.springdemo.entity.Roles;
import com.example.springdemo.repository.EmployeeRepository;
import com.example.springdemo.repository.EmployeeRolesRepository;
import com.example.springdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmployeeRolesRepository employeeRolesRepository;

    @GetMapping("/search-employee")
    public ResponseEntity<List<EmployeeWithRoleDTO>> getEmployeesByName(@RequestParam(required = false) String name) {
        System.out.println("get Employee: ");
        try {
            List<EmployeeWithRoleDTO> employeeWithRole = new ArrayList<>();
            List<Employees> employees = new ArrayList<Employees>();

            if (name.isEmpty()) {
                employeeRepository.findByIs_delete(false).forEach(employees::add);
            } else {
                employeeRepository.findByNameLikeIs_delete(name).forEach(employees::add);
            }
            for (Employees employee : employees) {
                Optional<Employee_Roles> employee_roles = employeeRolesRepository.findByEmployeeId(employee.getId());
                EmployeeWithRoleDTO dto = new EmployeeWithRoleDTO();
                dto.setEmployee(employee);
                if (employee_roles.isPresent()){
                    Employee_Roles employee_role = employee_roles.get();
                    Optional<Roles> roles = roleRepository.findById(employee_role.getRoles().getId());
                    dto.setRole(roles.get());
                }
                employeeWithRole.add(dto);
            }

            if (employeeWithRole.isEmpty()) {
                System.out.println("EWR empty");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employeeWithRole, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roles-list")
    public ResponseEntity<List<Roles>> getRoles() {
        System.out.println("get Roles: ");
        try {
            List<Roles> roles = new ArrayList<Roles>();
            roleRepository.findAll().forEach(roles::add);

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employees> createEmployees(@ModelAttribute Employees employee) {
        System.out.println("create: "+employee.getName()+" "+employee.getPassword());
        if(employee.getName().isEmpty()||employee.getPassword().isEmpty()){
            System.out.println("not empty");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (employeeRepository.findByName(employee.getName())!=null) {
            System.out.println("employee exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            Employees employees = employeeRepository.save(new Employees(employee.getName(), employee.getPassword(), false));
            return new ResponseEntity<>(employees, HttpStatus.CREATED);
        }

    }

    @PostMapping("/employee_role")
    public ResponseEntity<Employee_Roles> createEmployee_Role(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> employeeData = (Map<String, Object>) requestData.get("employeeData");
        Employees employee = new Employees();
        employee.setName((String) employeeData.get("name"));
        employee.setPassword((String) employeeData.get("password"));

        Integer role_id = (Integer) requestData.get("selectedRoleId");

        System.out.println("create employee with role: ");
        if(employee.getName().isEmpty()||employee.getPassword().isEmpty()){
            System.out.println("not blank");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (employeeRepository.findByName(employee.getName())!=null) {
            System.out.println("employee exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            Employees employees = employeeRepository.save(new Employees(employee.getName(), employee.getPassword(), false));
            System.out.println("new employee id: "+employees.getId());
            Roles roles = roleRepository.findById(role_id).get();
            Employee_Roles employee_roles = employeeRolesRepository.save(new Employee_Roles(employees, roles));
            return new ResponseEntity<>(employee_roles, HttpStatus.CREATED);
        }

    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employees> updateEmployees(@PathVariable("id") Integer id, @RequestBody Map<String, Object> requestData) {
        System.out.println("update employee: ");
        Optional<Employees> employees = employeeRepository.findById(id);
        Date date = new Date();

        if (employees.isPresent()) {
            Employees employee = employees.get();
            employee.setName((String) requestData.get("name"));
            employee.setPassword((String) requestData.get("password"));
            employee.setUpdated_at(date);

            Integer selectedRoleId = (Integer) requestData.get("selectedRoleId");
            if (selectedRoleId != null) {
                Optional<Roles> roles = roleRepository.findById(selectedRoleId);
                Optional<Employee_Roles> employee_roles = employeeRolesRepository.findByEmployeeId(employee.getId());
                Roles role = roles.get();
                if (employee_roles.isPresent()) {
                    Employee_Roles employee_role = employee_roles.get();
                    employee_role.setRoles(role);
                    employeeRolesRepository.save(employee_role);
                } else {
                    Employee_Roles employee_role = employeeRolesRepository.save(new Employee_Roles(employee, role));
                }
            }
            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employees> deleteEmployees(@PathVariable("id") Integer id) {
        System.out.println("delete: ");
        Optional<Employees> employees = employeeRepository.findById(id);

        if (employees.isPresent()) {
            Employees employee = employees.get();
            employee.setIs_delete(true);
            employeeRepository.save(employee);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
