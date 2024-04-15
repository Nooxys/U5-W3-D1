package CiroVitiello.U5W3D1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private String password;
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Device> devices;

    public Employee(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        setTemporaryAvatar();
    }

    public void setTemporaryAvatar() {
        this.avatar = "https://ui-avatars.com/api/?name=" + this.name + "+" + this.surname;
    }

}
