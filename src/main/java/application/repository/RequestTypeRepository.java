package application.repository;

import application.entity.request.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {

    RequestType findByName(String name);
}
