package com.tms.controllers;

import com.tms.models.Person;
import com.tms.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/all")
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();//JACKSON автоматически конвертирует объекты в JSON
    }
}

