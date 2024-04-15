package CiroVitiello.U5W3D1.services;

import CiroVitiello.U5W3D1.dto.EmployeeLoginDTO;
import CiroVitiello.U5W3D1.entities.Employee;
import CiroVitiello.U5W3D1.exceptions.UnauthorizedException;
import CiroVitiello.U5W3D1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeeService  es;

    @Autowired
    private JWTTools jt;

    public String authenticateEmployeesAndGenerateToken(EmployeeLoginDTO body){
        Employee employee = this.es.findByEmail(body.email());
        if(employee.getPassword().equals(body.password())){
            return jt.createToken(employee);

        } else {
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }

}
