package us.teachmeskills.springdataJpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.teachmeskills.springdataJpa.models.Person;
import us.teachmeskills.springdataJpa.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)//здесь для всех значение (по-умолчанию)  - read
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOneById(int id) {
        Optional<Person> foundPersonById = peopleRepository.findById(id);
        return foundPersonById.orElse(null);
    }

    @Transactional//здесь конкретно для метода уже работает по-другому!!!имеет преимущество
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person udatedPerson) {
        udatedPerson.setId(id);
        peopleRepository.save(udatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
