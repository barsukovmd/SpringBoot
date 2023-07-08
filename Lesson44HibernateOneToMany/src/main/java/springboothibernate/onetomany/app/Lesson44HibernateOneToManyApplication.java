package springboothibernate.onetomany.app;

import org.hibernate.Hibernate;
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

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека и закрыли сессию");
            session.getTransaction().commit();


            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            System.out.println("Внутри второй транзакции");
            person = session.merge(person);
            Hibernate.initialize(person.getItems());
            session.getTransaction().commit();

            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            System.out.println("Внутри третьей транзакции");
//            List<Item> itemList = session.createQuery("select i from Item i where i.owner.id=:personId", Item.class)
//                    .setParameter(person.getId(), "personId")
//                    .getResultList();
//            System.out.println(itemList);

            session.getTransaction().commit();

            System.out.println("вне сессии");
//            System.out.println(person.getItems());
        }
    }

}
