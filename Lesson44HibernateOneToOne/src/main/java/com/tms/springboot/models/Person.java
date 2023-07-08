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
@ToString
public class Person {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;


    @OneToOne(mappedBy = "owner")
    private Passport passport;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
        passport.setOwner(this);
    }
}
