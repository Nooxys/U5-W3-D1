package CiroVitiello.U5W3D1.controllers;


import CiroVitiello.U5W3D1.dto.NewEmployeeDTO;
import CiroVitiello.U5W3D1.entities.Employee;
import CiroVitiello.U5W3D1.exceptions.BadRequestException;
import CiroVitiello.U5W3D1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService es;


    @GetMapping
    private Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return this.es.getEmployees(page, size, sortBy);
    }



    @GetMapping("/{employeeId}")
    private Employee findEmployeeById(@PathVariable long employeeId) {
        return es.findById(employeeId);

    }

    @PutMapping("/{employeeId}")
    private Employee findEmployeeByIdAndUpdate(@PathVariable long employeeId, @RequestBody NewEmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return es.findByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findEmployeeByIdAndDelete(@PathVariable long employeeId) {
        es.findByIdAndDelete(employeeId);
    }

    @PostMapping("/upload/{employeeId}")
    public Employee uploadAvatar(@RequestParam("avatar") MultipartFile image, @PathVariable long employeeId) throws IOException {
        return es.uploadImage(image, employeeId);
    }

}
