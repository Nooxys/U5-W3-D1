package CiroVitiello.U5W3D1.controllers;

import CiroVitiello.U5W3D1.dto.EmployeeLoginDTO;
import CiroVitiello.U5W3D1.dto.EmployeeLoginResponseDTO;
import CiroVitiello.U5W3D1.dto.NewEmployeeDTO;
import CiroVitiello.U5W3D1.entities.Employee;
import CiroVitiello.U5W3D1.exceptions.BadRequestException;
import CiroVitiello.U5W3D1.services.AuthService;
import CiroVitiello.U5W3D1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private EmployeeService es;

    @Autowired
    private AuthService as;

    @PostMapping("/login")
    public EmployeeLoginResponseDTO login(@RequestBody EmployeeLoginDTO body){
        return new EmployeeLoginResponseDTO(this.as.authenticateEmployeesAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private Employee saveEmployee(@RequestBody @Validated NewEmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return es.save(body);
    }
}
