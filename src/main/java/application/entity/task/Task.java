package application.entity.task;

import application.entity.User;
import application.entity.task.TaskComment;
import application.entity.task.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;
    private String title;

    private String priority;
    private LocalDateTime issuedTime;
    private LocalDateTime dueTime;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatus status;

    private boolean personal;

    @OneToMany(mappedBy = "task")
    private Set<TaskComment> comments;


    @ManyToOne
    @JoinColumn(name = "issuer_id", nullable = false)
    private User issuer;

    @ManyToMany(mappedBy = "tasks")
    private Set<User> receivers;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;

}
