package com.tms.teachmeskills.entities;

import com.tms.teachmeskills.utils.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class User extends BaseEntity {

    private String name;
    private String surname;
    private String email;

    @PasswordConstraint
    private String password;
    private String birthday;
    private int balance;

}
