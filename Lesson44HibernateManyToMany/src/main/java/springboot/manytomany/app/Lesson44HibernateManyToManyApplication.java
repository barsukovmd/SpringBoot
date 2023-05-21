package springboot.manytomany.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboot.manytomany.models.Actor;
import springboot.manytomany.models.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Lesson44HibernateManyToManyApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);
        Session session;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Movie movie = new Movie("Marvel", 2019);
            Actor actor1 = new Actor("Actor 1 Marvel", 40);
            Actor actor2 = new Actor("Actor 2 Marvel", 30);

            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));

            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));

            session.persist(actor1);
            session.persist(actor2);
            session.persist(movie);

            session.getTransaction().commit();

        }
    }

}
