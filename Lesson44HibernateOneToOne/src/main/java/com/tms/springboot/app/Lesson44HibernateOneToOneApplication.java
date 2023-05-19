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


			Person person = Person.builder()
					.age(30)
					.name("Vadim")
					.build();

			Passport passport = Passport.builder()
					.passportNumber(4363576)
					.owner(person)
					.build();

			session.persist(person);
			session.persist(passport);

			session.getTransaction().commit();
		}
	}

}
