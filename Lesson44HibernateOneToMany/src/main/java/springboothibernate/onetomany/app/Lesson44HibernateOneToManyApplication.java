package springboothibernate.onetomany.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboothibernate.onetomany.models.Person;

@SpringBootApplication
public class Lesson44HibernateOneToManyApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();


            session.beginTransaction();
            session.getTransaction().commit();
        }
    }

}
