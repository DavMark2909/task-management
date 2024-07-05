package application.repository;

import application.entity.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("SELECT t FROM Task t WHERE t.issuer.username = :username")
    List<Request> getRequestByIssuer(@Param("username") String username);

    Optional<Request> findById(int id);
}