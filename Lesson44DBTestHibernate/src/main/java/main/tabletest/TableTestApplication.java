package main.tabletest;

import main.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TableTestApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            List<User> fromUser = session.createQuery("from User").getResultList();
            for (User users : fromUser) {
                System.out.println(users);
            }
            session.getTransaction().commit();
        }
    }
}
