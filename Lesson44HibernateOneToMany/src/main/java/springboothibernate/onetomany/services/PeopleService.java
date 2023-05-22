package springboothibernate.onetomany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboothibernate.onetomany.models.Person;
import springboothibernate.onetomany.repositories.PeopleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Transactional
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }


    @Transactional
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(LocalDate.now());
        person.setMood(HAPPY);
        peopleRepository.save(person);
    }

}
