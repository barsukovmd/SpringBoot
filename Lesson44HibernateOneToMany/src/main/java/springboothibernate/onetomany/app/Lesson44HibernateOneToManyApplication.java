package springboothibernate.onetomany.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboothibernate.onetomany.models.Item;
import springboothibernate.onetomany.models.Person;

@SpringBootApplication
public class Lesson44HibernateOneToManyApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);
        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();


            Person person = Person.builder()
                    .age(21)
                    .name("Person Cascade 2.0")
                    .build();


            person.addItems(new Item("item 1"));
            person.addItems(new Item("item 2"));
            person.addItems(new Item("item 3"));

            session.persist(person);

            session.getTransaction().commit();
        }
    }

}
