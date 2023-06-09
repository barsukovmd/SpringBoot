package us.teachmeskills.springcourse.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(schema = "students_db", name = "students")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "surname")
    private String surname;


    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private int age;
    @Column(name = "course")
    private int course;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    //EnumType.ORDINAL сохранение в таблицу будет в числовом варианте (от 0 - первое enum значение, до указанного значения)
    //!!! если изменить местами enum, то значения не поменяются, поэтому нужно быть аккуратным

    // EnumType.STRING сохранение в таблицу будет в формате String
    private Mood mood;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate createdAt;

}
