package springboothibernate.onetomany.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboothibernate.onetomany.models.Item;
import springboothibernate.onetomany.models.Person;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
public class Lesson44HibernateOneToManyApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = Person.builder()
                    .age(28)
                    .name("Kolya 2.0")
                    .build();

            Item newItem = Item.builder()
                    .itemName("Ferrari 2.0 или Iphone 100 MaxMiniMax")
                    .owner(person)//указали и здесь !!!
                    .build();

            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));//указали и здесь !!!

            session.persist(person);
            session.persist(newItem);
//
//            Person person = session.get(Person.class, 4);
//            Item item = session.get(Item.class, 1);
//            person.getItems().remove(item);
//            item.setOwner(person);
//            person.getItems().add(item);

            session.getTransaction().commit();
        }
    }

}
