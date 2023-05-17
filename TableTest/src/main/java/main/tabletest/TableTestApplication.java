package main.tabletest;

import main.models.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:hibernate.properties")
public class TableTestApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Students.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Students students = session.get(Students.class, 10);
        System.out.println(students.getName());
        System.out.println(students.getSurname());
        session.getTransaction().commit();
        sessionFactory.close();
    }
}
