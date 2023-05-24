package us.teachmeskills.springdataJpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.teachmeskills.springdataJpa.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
