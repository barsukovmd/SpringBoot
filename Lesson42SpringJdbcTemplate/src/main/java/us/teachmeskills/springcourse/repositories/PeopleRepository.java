package us.teachmeskills.springcourse.repositories;

import org.springframework.stereotype.Repository;
import us.teachmeskills.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository {
    List<Person> findAll();

    Optional<Person> findById(int id);

    void save(Person person);

}
