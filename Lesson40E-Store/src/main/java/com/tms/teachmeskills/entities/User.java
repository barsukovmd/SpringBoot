package com.tms.teachmeskills.entities;

import com.tms.teachmeskills.utils.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Your name is empty")
    @Size(min = 1, max = 20, message = "name should contains between 1 and 30 symbols")
    private String name;
    private String surname;
    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;


    @PasswordConstraint
    private String password;
    private String birthday;

    @Min(value = 0, message = "price should be greater than 0")
    private int balance;

}
