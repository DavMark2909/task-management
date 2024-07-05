package application.repository;

import application.entity.request.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer> {

    RequestStatus findByName(String name);
}
