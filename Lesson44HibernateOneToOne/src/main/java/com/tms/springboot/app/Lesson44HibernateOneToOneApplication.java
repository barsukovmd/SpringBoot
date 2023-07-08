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

			Person personSet = session.get(Person.class, 1);
			session.remove(personSet);

			session.getTransaction().commit();
		}
	}

}
