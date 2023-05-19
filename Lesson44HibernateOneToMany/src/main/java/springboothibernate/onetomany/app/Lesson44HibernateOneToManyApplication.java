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

//            Person person = Person.builder()
//                    .age(30)
//                    .name("Serega hibernate 2.0")
//                    .build();
//            Item newItem = Item.builder()
//                    .itemName("Refrigerator hibernate 2.0")
//                    .owner(person)//указали и здесь !!!
//                    .build();
//            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));//указали и здесь !!!
//
//            session.persist(person);
//            session.persist(newItem);

            Person person = session.get(Person.class, 7);
            session.remove(person);
            person.getItems().forEach(item -> item.setOwner(null));
            session.getTransaction().commit();
        }
    }

}
