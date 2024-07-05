package application.entity.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request_type")
public class RequestType {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "type")
    private List<Request> requests;
}
