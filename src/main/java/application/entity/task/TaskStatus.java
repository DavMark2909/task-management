package application.entity.task;

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
@Table(name = "task_status")
public class TaskStatus {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "status")
    private Set<Task> tasks;
}
