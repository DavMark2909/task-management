package application.entity.request;

import application.entity.User;
import application.entity.request.RequestComment;
import application.entity.request.RequestStatus;
import application.entity.request.RequestType;
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
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private RequestType type;

    @ManyToMany(mappedBy = "requests")
    private Set<User> receivers;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "issuer_id", nullable = false)
    private User issuer;

    @OneToMany(mappedBy = "request")
    private Set<RequestComment> comments;

}
