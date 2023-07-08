package com.tms.springboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "passport", name = "passport")
@ToString
public class Passport implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "passport_number")
    private int passportNumber;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", unique = true)
    private Person owner;


    public Passport(int passportNumber) {
        this.passportNumber = passportNumber;
    }
}
