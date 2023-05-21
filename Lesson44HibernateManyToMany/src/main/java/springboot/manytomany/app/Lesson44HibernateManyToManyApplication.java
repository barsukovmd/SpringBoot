package springboot.manytomany.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboot.manytomany.models.Actor;
import springboot.manytomany.models.Movie;

import java.util.ArrayList;
import java.util.Collections;
@SpringBootApplication
public class Lesson44HibernateManyToManyApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);
        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Movie movie = new Movie("My movie", 2023);
            Actor actor = new Actor("Nikolai", 20);
            actor.setMovies(new ArrayList<>(Collections.singletonList(movie)));

            session.save(movie);
            session.save(actor);

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
