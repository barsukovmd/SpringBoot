create table actors_movies.actor
(
    id   int generated by default as identity primary key,
    name varchar(100) not null unique,
    age  int check ( age > 0 )
);
create table actors_movies.movie
(
    id                 int generated by default as identity primary key,
    name               varchar(100) not null,
    year_of_production int check ( year_of_production > 1900 )
);
--creating JOIN table
create table actors_movies.join_a_m
(
    actor_id int references actors_movies.actor (id),
    movie_id int references actors_movies.movie (id),
    primary key (actor_id, movie_id)
);

