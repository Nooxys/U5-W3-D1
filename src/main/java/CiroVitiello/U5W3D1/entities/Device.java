package CiroVitiello.U5W3D1.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String typology;
    private String status = "Available";
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(String typology) {
        this.typology = typology;
    }

    public Device(String typology, Employee employee) {
        this.typology = typology;
        this.employee = employee;
    }
}
