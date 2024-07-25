package application.repository;

import application.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {
    Optional<ItemType> findByName(String name);

    @Query("SELECT DISTINCT type.name from ItemType type")
    List<String> getAllCategories();
}
