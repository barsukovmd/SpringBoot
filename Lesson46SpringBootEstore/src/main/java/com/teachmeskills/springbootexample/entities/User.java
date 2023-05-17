package com.teachmeskills.springbootexample.entities;

import com.teachmeskills.springbootexample.utils.PasswordConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "eshop", name = "users")

public class User extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @PasswordConstraint
    @Column(name = "password")
    private String password;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "balance")
    private int balance;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> orders;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        User user = (User) o;
//        return id != null && Objects.equals(id, user.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
