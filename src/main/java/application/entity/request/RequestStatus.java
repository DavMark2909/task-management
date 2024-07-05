package application.entity.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request_status")
public class RequestStatus {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "status")
    private Set<Request> requests;
}
