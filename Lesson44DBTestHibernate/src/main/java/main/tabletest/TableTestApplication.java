package main.tabletest;

import main.models.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class TableTestApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Users.class);
        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            System.out.println(session.get(Users.class, 1));

            Users u1 = Users.builder()
                    .birthday(LocalDate.of(2002, 2, 13))
                    .email("vivat@hotmail.com")
                    .name("Lily")
                    .password("qazsdre")
                    .surname("Marvel")
                    .balance(1085867)
                    .build();
            Users u2 = Users.builder()
                    .birthday(LocalDate.of(1997, 2, 10))
                    .email("rama@mail.ru")
                    .name("John")
                    .password("lfkkewfo")
                    .surname("Kirby")
                    .balance(769867)
                    .build();
            Users u3 = Users.builder()
                    .birthday(LocalDate.of(1995, 2, 9))
                    .email("ronald@google.com")
                    .name("Kiryl")
                    .password("iuhgwfiublhwe")
                    .surname("Giovanni")
                    .balance(965786)
                    .build();

            session.persist(u1);
            session.persist(u2);
            session.persist(u3);

            session.getTransaction().commit();
        }
    }
}
