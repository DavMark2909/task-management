package application.entity;


import application.entity.request.Request;
import application.entity.task.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    @Column(name = "lastname")
    private String lastName;
    private String username;
    private String password;

//    will be used for the track of a chat room with system notifications
    @Column(name="chat_id")
    private int chatId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Set<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_request",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "request_id"))
    private Set<Request> requests;

    @OneToMany(mappedBy = "issuer")
    private Set<Task> issuedTasks;

    //    i have to create one super user for each role. for example, if a banch of workers did one job together
    @OneToMany(mappedBy = "performer")
    private Set<Task> completedTasks;

    @OneToMany(mappedBy = "issuer")
    private Set<Request> issuedRequest;
}
