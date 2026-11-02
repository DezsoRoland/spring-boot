package com.amigoscode.person;

import com.amigoscode.SortingOrder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/person")
public class    PersonController {
    private final PersonService personService;
    private final Validator validator;

    public PersonController(PersonService personService, Validator validator) {
        this.personService = personService;
        this.validator = validator;
    }


    @GetMapping
    public List<Person> getPerson (
            @RequestParam(value = "sort", required = false, defaultValue = "DESC" ) SortingOrder sort,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
       return personService.getPerson(sort);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(
            @Valid @Positive @PathVariable("id") Integer id
    ){
        Person p = personService.getPersonById(id);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@Valid @Positive @PathVariable("id") Integer id) {
        personService.deletePersonById(id);
    }

    @PostMapping
    public void addPerson(@RequestBody NewPersonRequest person){
        Set<ConstraintViolation<NewPersonRequest>> validate = validator.validate(person);
        validate.forEach(e -> System.out.println(e.getMessage()));
        if(!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
        personService.addPerson(person);
    }

    @PutMapping("{id}")
    public void updatePerson(
            @PathVariable("id") Integer id,
            @RequestBody PersonUpdate update
    ){
        personService.updatePerson(id, update);
    }
}
