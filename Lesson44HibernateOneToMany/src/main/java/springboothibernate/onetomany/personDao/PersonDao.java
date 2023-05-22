package springboothibernate.onetomany.personDao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springboothibernate.onetomany.models.Person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDao {
    private final EntityManager entityManager;

    @Autowired
    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Person", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = entityManager.unwrap(Session.class);
        Person person = session.get(Person.class, id);
        person.setName(updatedPerson.getName());
        person.setId(updatedPerson.getId());
        person.setItems(updatedPerson.getItems());
    }

    @Transactional
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(session.get(Person.class, id));
    }

    @Transactional
    public void testNPlus1() {
        Session session = entityManager.unwrap(Session.class);
        Set<Person> people = new HashSet<>(session.createQuery("select p from Person p LEFT join fetch p.items", Person.class)
                .getResultList());

        for (Person person : people) {
            System.out.println("Person " + person.getName() + " has " + person.getItems());
        }

    }
}
