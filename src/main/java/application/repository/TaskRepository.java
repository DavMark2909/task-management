package application.repository;

import application.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findTaskById(Integer id);

    @Query("SELECT t FROM Task t WHERE t.issuer.username = :username")
    List<Task> findTasksByIssuerUsername(@Param("username") String username);

    @Query("SELECT t FROM Task t WHERE t.performer.username = :username")
    List<Task> findTasksByPerformerUsername(@Param("username") String username);
}
