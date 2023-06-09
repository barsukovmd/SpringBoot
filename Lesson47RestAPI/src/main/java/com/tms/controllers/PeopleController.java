package com.tms.controllers;

import com.tms.models.Person;
import com.tms.services.PeopleService;
import com.tms.utils.PersonErrorResponse;
import com.tms.utils.PersonNotCreatedException;
import com.tms.utils.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping("/all")
    public List<Person> getPeople() {
        return peopleService.findAll();//JACKSON автоматически конвертирует объекты в JSON
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Validated Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMessage = new StringBuilder();
            List<FieldError> fieldError = bindingResult.getFieldErrors();
            for (FieldError error : fieldError) {
                errMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errMessage.toString());
        }
        peopleService.save(person);
        // отправляем НТТР ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException exception) {
        PersonErrorResponse personNotFoundErrorResponse = new PersonErrorResponse(
                "Person was not found with this id",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personNotFoundErrorResponse, HttpStatus.NOT_FOUND); //NOT_FOUND - 404 cтатус
    }

    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException exception) {
        PersonErrorResponse personNotCreatedErrorResponse = new PersonErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personNotCreatedErrorResponse, HttpStatus.BAD_REQUEST);
    }
}

