package application.repository;

import application.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByName(String name);

    Optional<Item> findById(int id);

    @Query(value = "SELECT i FROM Item i WHERE i.type.name = :typeName")
    List<Item> findAllByType(@Param("typeName") String typeName);
}
