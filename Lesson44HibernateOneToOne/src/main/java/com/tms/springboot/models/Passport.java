package com.tms.springboot.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

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
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "passport_number")
    private int passportNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Passport passport = (Passport) o;
        return getOwner() != null && Objects.equals(getOwner(), passport.getOwner());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
