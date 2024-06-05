package application.repository;

import application.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {
    Optional<ItemType> findByName(String name);
}
