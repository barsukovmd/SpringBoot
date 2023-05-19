package com.tms.springboot.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "passport", name = "person")
public class Person {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;


    @OneToOne(mappedBy = "owner")
    private Passport passport;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", passport=" + passport +
                '}';
    }
}
