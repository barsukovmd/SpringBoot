package com.tms.estore.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
