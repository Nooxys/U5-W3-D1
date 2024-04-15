package CiroVitiello.U5W3D1.security;

import CiroVitiello.U5W3D1.entities.Employee;
import CiroVitiello.U5W3D1.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

public String createToken(Employee employee){
    return Jwts.builder()
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .subject(String.valueOf(employee.getId()))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
}

public void verifyToken(String token){
    try {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parse(token);
    } catch (Exception ex){
        throw new UnauthorizedException("Problems with token! Please login again!");
    }
}

}
