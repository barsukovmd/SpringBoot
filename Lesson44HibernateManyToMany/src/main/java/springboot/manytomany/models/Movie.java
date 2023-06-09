package springboot.manytomany.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "actors_movies", name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_of_production")
    private int yearOfProduction;

    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    // many-to-one или one-to-one (по-умолчанию EAGER НЕ ЛЕНИВАЯ загрузка)
    //one-to-many или many-to-many (по-умолчанию LAZY ЛЕНИВАЯ загрузка)
    private List<Actor> actors;


    public Movie(String name, int yearOfProduction) {
        this.name = name;
        this.yearOfProduction = yearOfProduction;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", yearOfProduction=" + yearOfProduction +
                ", actors=" + actors +
                '}';
    }
}
