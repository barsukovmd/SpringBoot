package us.teachmeskills.springdataJpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.teachmeskills.springdataJpa.models.Item;
import us.teachmeskills.springdataJpa.models.Person;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<ItemsRepository, Integer> {
    List<Item> findByItemName(String itemName);

    //person.getItems() равнозначно|
    List<Item> findByOwner(Person owner);
}
