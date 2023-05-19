package com.tms.springboot.app;

import com.tms.springboot.models.Passport;
import com.tms.springboot.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson44HibernateOneToOneApplication {

	public static void main(String[] args) {
		Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Passport.class);
		Session session;
		try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
			session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Person person = new Person("Yauhen", 25);
			Passport passport = new Passport(person, 49853745);
			person.setPassport(passport);

			session.persist(person);
			session.persist(passport);

			session.getTransaction().commit();
		}
	}

}
