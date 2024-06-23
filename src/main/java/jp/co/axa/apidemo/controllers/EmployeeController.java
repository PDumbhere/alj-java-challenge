package jp.co.axa.apidemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@ApiResponse(responseCode = "200", description = "HTTP Status Successfull")
@Tag(
        name = "REST APIs Employee data CRUD operation",
        description = "Can create new employees, delete them, update them and also retrive that data"
)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get All Employees REST API", description = "Get Employee REST API is used to Retrive all employees from Database")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get Employee By Id REST API", description = "Get Employee By Id REST API is used to Retrive Employee using Employee Id from Database")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(name="employeeId")Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @Operation(summary = "Create Employee REST API", description = "Create Employee REST API is used to save Employee to database")

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 CREATED")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employees")
    public ResponseEntity<String> saveEmployee(Employee employee){
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>("Employee Saved Successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Employee REST API", description = "Delete Employee REST API is used to delete Employee from database")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }

    @Operation(summary = "Update Employee REST API", description = "Update Employee REST API is used to Update Employee in database")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employee);
        }
        return ResponseEntity.ok("Employee Updated Successfully");
    }

}
